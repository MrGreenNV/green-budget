package ru.averkiev.gateway.services;

import ru.averkiev.gateway.dto.TransactionCategoryDTO;
import ru.averkiev.gateway.views.TransactionCategoryModelView;

import java.util.List;

public interface TransactionCategoryService {
    TransactionCategoryModelView createCategory(final TransactionCategoryDTO transactionCategoryDTO);

    TransactionCategoryModelView updateCategory(final Long categoryId, final TransactionCategoryDTO transactionCategoryDTO);

    TransactionCategoryModelView getInfoCategory(final Long categoryId);

    TransactionCategoryModelView getInfoActiveCategory(final Long categoryId);

    List<TransactionCategoryModelView> getAllCategories();

    List<TransactionCategoryModelView> getAllActiveCategories();

    void softDeleteCategory(final Long categoryId);
}
