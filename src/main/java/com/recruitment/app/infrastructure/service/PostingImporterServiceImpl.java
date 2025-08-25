package com.recruitment.app.infrastructure.service;

import com.recruitment.app.domain.model.Employee;
import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.utils.CareerFieldMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class PostingImporterServiceImpl implements PostingImporterService{
    private static final int REQUEST_TIMEOUT = 10000;
    private static final int AWAIT_TERMINATION_MINUTES = 5;
    private static final int THREAD_POOL_SIZE = 16;
    private static final String baseUrl = "https://jobs.lever.co/useinsider";

    @Override
    public PostingImportResult fetchPostings() {
        ConcurrentLinkedQueue<Posting> scrapedPostings = new ConcurrentLinkedQueue<>();
        PostingImportResult result = new PostingImportResult();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try {
            Document doc = Jsoup.connect(baseUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                    .timeout(REQUEST_TIMEOUT)
                    .get();

            List<String> postingUrls = doc.select("a.posting-title").eachAttr("href");
            result.setTotalPostings(postingUrls.size());

            for(String postingUrl : postingUrls){
                executor.submit(() -> {
                    try {
                        scrapedPostings.add(processJob(postingUrl));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }

        try{
            if(!executor.awaitTermination(AWAIT_TERMINATION_MINUTES, TimeUnit.MINUTES)){
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        result.setPostings(scrapedPostings);
        return result;
    }

    private Posting processJob(String jobUrl){
        Posting posting = new Posting();
        try {
            Document jobPage = Jsoup.connect(jobUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                    .timeout(REQUEST_TIMEOUT)
                    .get();



            String[] splitUrl = jobUrl.split("/");
            UUID id = UUID.fromString(splitUrl[splitUrl.length - 1]);
            posting.setId(id);

            Elements postingCategories = jobPage.select("div.posting-categories");

            String careerField = postingCategories.select(".department").text();
            careerField = careerField.substring(0, careerField.length() - 2);
            posting.setCareerField(careerField);
            posting.setDepartment(CareerFieldMapper.getValue(careerField));

            Optional.ofNullable(jobPage.selectFirst("div.posting-headline > h2"))
                            .map(Element::text)
                            .ifPresentOrElse(posting::setTitle, () -> posting.setTitle("Unknown"));
            Optional.ofNullable(postingCategories.selectFirst(".location"))
                    .map(Element::text)
                    .ifPresentOrElse(posting::setLocation, () -> posting.setLocation("Unknown"));
            Optional.ofNullable(postingCategories.selectFirst(".commitment"))
                    .map(Element::text)
                    .ifPresentOrElse(posting::setWorkingType, () -> posting.setWorkingType("Unknown"));
            Optional.ofNullable(postingCategories.selectFirst(".workplaceTypes"))
                    .map(Element::text)
                    .ifPresentOrElse(posting::setWorkPlace, () -> posting.setWorkPlace("Unknown"));
            posting.setStatus("ACTIVE");

            Elements sections = jobPage.select("div.section-wrapper.page-full-width").get(2).select("div.section.page-centered:not([data-qa])").stream().
                    limit(2).collect(Collectors.toCollection(Elements::new));

            StringBuilder sb = new StringBuilder();
            for (Element section : sections) {
                sb.append(section.select("h3").text()).append("\n");
                    Elements listItems = section.selectFirst("ul.posting-requirements.plain-list > ul").children();
                        for (Element item : listItems) {
                            String nodeText = item.text();
                            sb.append("• %s".formatted(nodeText)).append("\n");
                }
            }

            posting.setDetails(sb.toString());
            posting.setLevel("Unknown");
            posting.setKeywords("None");
            return posting;
        } catch (Exception ignored) {
            System.err.println("timeout");
        }
        return null;
    }
}
