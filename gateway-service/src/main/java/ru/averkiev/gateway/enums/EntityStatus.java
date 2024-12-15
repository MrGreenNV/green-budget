package ru.averkiev.gateway.enums;

import jakarta.persistence.EntityNotFoundException;

@SuppressWarnings("unused")
public enum EntityStatus {
    ACTIVE(0, "active"),
    NOT_ACTIVE(1, "not_active"),
    DELETED(2, "deleted");

    private final int id;
    private final String name;

    EntityStatus(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static EntityStatus fromId(final int id) {
        for (EntityStatus entityStatus : EntityStatus.values()) {
            if (entityStatus.id == id)
                return entityStatus;
        }
        throw new EntityNotFoundException(String.format("Статус с id = %d не найден", id));
    }

    public static EntityStatus fromName(final String name) {
        for (EntityStatus entityStatus : EntityStatus.values()) {
            if (entityStatus.name.equalsIgnoreCase(name))
                return entityStatus;
        }
        throw new EntityNotFoundException(String.format("Статус с названием = %s не найден", name));
    }
}
