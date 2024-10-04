package ru.averkiev.budget.exceptions;

@SuppressWarnings("unused")
public class UsernameValidationException extends RuntimeException {
    public UsernameValidationException() {
        super();
    }

    public UsernameValidationException(String message) {
        super(message);
    }

    public UsernameValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
