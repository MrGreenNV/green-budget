package ru.averkiev.budget.exceptions;

@SuppressWarnings("unused")
public class TransactionTypeNotFound extends RuntimeException {
    public TransactionTypeNotFound() {
    }

    public TransactionTypeNotFound(String message) {
        super(message);
    }

    public TransactionTypeNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
