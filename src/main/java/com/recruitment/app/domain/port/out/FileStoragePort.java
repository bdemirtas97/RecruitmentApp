package com.recruitment.app.domain.port.out;

import java.io.IOException;
import java.io.InputStream;

public interface FileStoragePort {
    String addResume(String objectKey, InputStream inputStream) throws IOException;
}