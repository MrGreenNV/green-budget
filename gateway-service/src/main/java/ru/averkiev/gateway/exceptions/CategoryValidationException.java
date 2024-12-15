package ru.averkiev.gateway.exceptions;

@SuppressWarnings("unused")
public class CategoryValidationException extends RuntimeException {
    public CategoryValidationException() {
    }

    public CategoryValidationException(String message) {
        super(message);
    }

    public CategoryValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
