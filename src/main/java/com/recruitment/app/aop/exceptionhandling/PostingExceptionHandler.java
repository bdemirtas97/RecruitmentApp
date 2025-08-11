package com.recruitment.app.aop.exceptionhandling;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
@RequiredArgsConstructor
public class PostingExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(PostingExceptionHandler.class);


}
