package ru.averkiev.gateway.enums;

import ru.averkiev.gateway.exceptions.TransactionTypeNotFound;

@SuppressWarnings("unused")
public enum TransactionType {
    EXPENSE(0, "expense"),
    INCOME(1, "income");

    private final int id;
    private final String name;

    TransactionType(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static TransactionType fromId(final int id) {
        for (TransactionType type : TransactionType.values()) {
            if (type.id == id)
                return type;
        }
        throw new TransactionTypeNotFound(String.format("Транзакция с id = %d не найдена", id));
    }

    public static TransactionType fromName(final String name) {
        for (TransactionType type : TransactionType.values()) {
            if (type.name.equalsIgnoreCase(name))
                return type;
        }
        throw new TransactionTypeNotFound(String.format("Транзакция с названием = %s не найдена", name));
    }
}
