package com.recruitment.app.infrastructure.storage;

import com.recruitment.app.domain.port.out.FileStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Repository
public class S3StorageAdapter implements FileStoragePort {
    private final S3Client s3Client;
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3StorageAdapter(S3Client s3Client){
        this.s3Client = s3Client;
    }
    @Override
    public String addResume(String objectKey, InputStream inputStream) throws IOException {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        RequestBody requestBody = RequestBody.fromInputStream(inputStream, inputStream.available());
        s3Client.putObject(objectRequest, requestBody);

        GetUrlRequest urlRequest = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        URL url = s3Client.utilities().getUrl(urlRequest);
        return url.toExternalForm();
    }
}
