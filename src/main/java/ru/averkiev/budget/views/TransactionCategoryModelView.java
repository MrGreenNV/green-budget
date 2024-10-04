package ru.averkiev.budget.views;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@SuppressWarnings("unused")
public class TransactionCategoryModelView {
    private String name;

    @JsonManagedReference
    private List<TransactionCategoryModelView> childCategories;

    @JsonBackReference
    private TransactionCategoryModelView parentCategory;

    public TransactionCategoryModelView() {
    }

    public TransactionCategoryModelView(String name, List<TransactionCategoryModelView> childCategories, TransactionCategoryModelView parentCategory) {
        this.name = name;
        this.childCategories = childCategories;
        this.parentCategory = parentCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TransactionCategoryModelView> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<TransactionCategoryModelView> childCategories) {
        this.childCategories = childCategories;
    }

    public TransactionCategoryModelView getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(TransactionCategoryModelView parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public String toString() {
        return "TransactionCategoryModelView{" +
                "name='" + name + '\'' +
                ", childCategories=" + childCategories +
                ", parentCategory=" + parentCategory +
                '}';
    }
}
