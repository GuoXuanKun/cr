package com.example.cr.common.exception;

/**
 * Base class for all custom exceptions in the application
 */
public abstract class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
