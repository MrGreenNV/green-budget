package ru.averkiev.gateway.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "category_of_transactions")
@SuppressWarnings("unused")
public class TransactionCategory extends BaseEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<TransactionCategory> childCategories;

    @ManyToOne
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    private TransactionCategory parentCategory;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;

    public TransactionCategory(String name, List<TransactionCategory> childCategories, TransactionCategory parentCategory) {
        this.name = name;
        this.childCategories = childCategories;
        this.parentCategory = parentCategory;
    }

    public TransactionCategory(long id, Date createdAt, Date updatedAt, String name, List<TransactionCategory> childCategories, TransactionCategory parentCategory) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.childCategories = childCategories;
        this.parentCategory = parentCategory;
    }

    public TransactionCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TransactionCategory> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<TransactionCategory> childCategories) {
        this.childCategories = childCategories;
    }

    public TransactionCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(TransactionCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionCategory that = (TransactionCategory) o;
        return Objects.equals(name, that.name) && Objects.equals(childCategories, that.childCategories) && Objects.equals(parentCategory, that.parentCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, childCategories, parentCategory);
    }

    @Override
    public String toString() {
        return "TransactionCategory{" +
                "name='" + name + '\'' +
                ", childCategories=" + childCategories +
                ", parentCategory=" + parentCategory +
                '}';
    }
}
