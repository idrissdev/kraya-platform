package com.kraya.platform.exception;

/**
 * Exception thrown when an invalid role is provided.
 */
public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String message) {
        super(message);
    }
}
