package ru.averkiev.budget.dto;

@SuppressWarnings("unused")
public class TransactionCategoryDTO {
    private String name;
    private Long parent;

    public TransactionCategoryDTO() {
    }

    public TransactionCategoryDTO(final String name, final Long parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(final Long parent) {
        this.parent = parent;
    }
}
