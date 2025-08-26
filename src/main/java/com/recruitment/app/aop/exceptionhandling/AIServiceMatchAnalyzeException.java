package com.recruitment.app.aop.exceptionhandling;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class AIServiceMatchAnalyzeException extends RuntimeException {
    private final String responseBody;
    private final HttpStatusCode status;
    public AIServiceMatchAnalyzeException(String message, String responseBody, HttpStatusCode status) {
        super(message);
        this.responseBody = responseBody;
        this.status = status;
    }
}
