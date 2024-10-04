package ru.averkiev.budget.exceptions;

@SuppressWarnings("unused")
public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super();
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }

    public EmailAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
