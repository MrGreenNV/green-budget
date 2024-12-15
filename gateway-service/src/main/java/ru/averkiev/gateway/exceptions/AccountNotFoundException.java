package ru.averkiev.gateway.exceptions;

@SuppressWarnings("unused")
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
