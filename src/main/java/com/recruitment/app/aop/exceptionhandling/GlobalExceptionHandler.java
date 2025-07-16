package com.recruitment.app.aop.exceptionhandling;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Specifically handles and silences the harmless NoResourceFoundException,
     * which is often triggered by browser requests for "favicon.ico".
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNoResourceFound(NoResourceFoundException ex) {
        logger.warn("Resource not found: {}", ex.getResourcePath());
        // Method is empty to "swallow" the exception and prevent verbose logging for common 404s like favicons.
    }

    /**
     * Handles exceptions for when a database entity (like a Candidate) cannot be found.
     * Renders the 404 error page.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleEntityNotFound(EntityNotFoundException ex) {
        logger.error("Entity not found exception: {}", ex.getMessage());
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }

    /**
     * Handles business rule violations, such as trying to sign up with an existing email.
     * Renders a generic error page with a 409 Conflict status.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ModelAndView handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Business rule violation: {}", ex.getMessage());
        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }

    /**
     * A catch-all for any other unexpected exceptions.
     * Renders the 500 internal server error page.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleGeneralException(Exception ex) {
        logger.error("An unexpected error occurred", ex);
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("errorMessage", "An unexpected server error occurred. Please try again later.");
        return mav;
    }
}