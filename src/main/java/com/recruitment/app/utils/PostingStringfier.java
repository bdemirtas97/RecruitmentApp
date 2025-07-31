package com.recruitment.app.utils;

import com.recruitment.app.domain.model.Posting;

public class PostingStringfier {
    public static String fieldsToString(Posting posting)  {
        StringBuilder result = new StringBuilder();
        result.append("%s: %s; ".formatted("Title", posting.getTitle()));
        result.append("%s: %s; ".formatted("Location", posting.getLocation()));
        result.append("%s: %s; ".formatted("Level", posting.getTitle()));
        result.append("%s: %s; ".formatted("Title", posting.getLevel()));
        result.append("%s: %s; ".formatted("Working Type", posting.getWorkingType()));
        result.append("%s: %s; ".formatted("Work Place", posting.getWorkPlace()));
        result.append("%s: %s; ".formatted("Details", posting.getDetails()));
        result.append("%s: %s; ".formatted("Keywords", posting.getKeywords()));

        if (result.length() > 2) {
            result.setLength(result.length() - 2);
        }

        return result.toString();
    }
}
