package ru.averkiev.budget.exceptions;

@SuppressWarnings("unused")
public class EmailValidationException extends RuntimeException {
    public EmailValidationException() {
        super();
    }

    public EmailValidationException(String message) {
        super(message);
    }

    public EmailValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
