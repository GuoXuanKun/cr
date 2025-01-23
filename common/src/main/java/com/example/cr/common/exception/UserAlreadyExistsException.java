package com.example.cr.common.exception;

/**
 * Exception thrown when attempting to register a user that already exists
 */
public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
