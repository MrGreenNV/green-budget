package ru.averkiev.gateway.exceptions;

public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException() {
    }

    public CategoryAlreadyExistException(String message) {
        super(message);
    }

    public CategoryAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
