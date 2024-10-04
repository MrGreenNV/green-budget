package ru.averkiev.budget.exceptions;

@SuppressWarnings("unused")
public class PasswordAlreadyExistException extends RuntimeException {
    public PasswordAlreadyExistException() {
        super();
    }

    public PasswordAlreadyExistException(String message) {
        super(message);
    }

    public PasswordAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
