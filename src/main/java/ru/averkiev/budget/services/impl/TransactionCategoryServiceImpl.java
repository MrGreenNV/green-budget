package ru.averkiev.budget.services.impl;

import io.micrometer.common.util.internal.logging.InternalLogger;
import io.micrometer.common.util.internal.logging.InternalLoggerFactory;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.averkiev.budget.dto.TransactionCategoryDTO;
import ru.averkiev.budget.enums.EntityStatus;
import ru.averkiev.budget.exceptions.CategoryAlreadyExistException;
import ru.averkiev.budget.exceptions.CategoryNotFoundException;
import ru.averkiev.budget.exceptions.CategoryValidationException;
import ru.averkiev.budget.models.TransactionCategory;
import ru.averkiev.budget.repositories.TransactionCategoryRepository;
import ru.averkiev.budget.services.TransactionCategoryService;
import ru.averkiev.budget.views.TransactionCategoryModelView;

import java.util.List;

import static ru.averkiev.budget.utils.Utils.encodeToBase64;

@Service
@SuppressWarnings("unused")
public class TransactionCategoryServiceImpl implements TransactionCategoryService {
    private final TransactionCategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final InternalLogger logger = InternalLoggerFactory.getInstance(this.getClass());

    @Autowired
    public TransactionCategoryServiceImpl(TransactionCategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionCategoryModelView createCategory(TransactionCategoryDTO transactionCategoryDTO) {
        final TransactionCategory category = modelMapper.map(transactionCategoryDTO, TransactionCategory.class);
        final String categoryName = transactionCategoryDTO.getName().toLowerCase();
        final Long parentId = transactionCategoryDTO.getParent();

        this.checkForEmptyCategoryName(categoryName);
        this.checkUniqueCategoryName(categoryName);

        category.setName(categoryName);
        category.setParentCategory(parentId == null ? null : findActiveCategoryByIdOrElseThrow(parentId, categoryRepository));
        category.setStatus(EntityStatus.ACTIVE);

        final TransactionCategory savedCategory = categoryRepository.save(category);
        logger.info("Категория {} успешно сохранена", categoryName);
        return modelMapper.map(savedCategory, TransactionCategoryModelView.class);
    }

    @Override
    public TransactionCategoryModelView updateCategory(final Long categoryId, final TransactionCategoryDTO transactionCategoryDTO) {
        final TransactionCategory savedCategory = modelMapper.map(findActiveCategoryByIdOrElseThrow(categoryId, categoryRepository), TransactionCategory.class);
        final TransactionCategory updatedCategory = modelMapper.map(transactionCategoryDTO, TransactionCategory.class);

        this.updateCategoryName(savedCategory, updatedCategory);
        final TransactionCategory hasUpdatedCategory = categoryRepository.save(savedCategory);
        final String categoryName = savedCategory.getName();
        logger.info("Категория {} успешно обновлена", categoryName);
        return modelMapper.map(savedCategory, TransactionCategoryModelView.class);
    }

    @Override
    public TransactionCategoryModelView getInfoCategory(Long categoryId) {
        final TransactionCategory category = this.findCategoryByIdOrElseThrow(categoryId);
        return modelMapper.map(category, TransactionCategoryModelView.class);
    }

    @Override
    public TransactionCategoryModelView getInfoActiveCategory(Long categoryId) {
        final TransactionCategory category = findActiveCategoryByIdOrElseThrow(categoryId, categoryRepository);
        return modelMapper.map(category, TransactionCategoryModelView.class);
    }

    @Override
    public List<TransactionCategoryModelView> getAllCategories() {
        return categoryRepository
                .findAll().stream()
                .map(category -> modelMapper.map(category, TransactionCategoryModelView.class))
                .toList();
    }

    @Override
    public List<TransactionCategoryModelView> getAllActiveCategories() {
        return categoryRepository
                .findAll().stream()
                .filter(category -> category.getStatus().equals(EntityStatus.ACTIVE))
                .map(category -> modelMapper.map(category, TransactionCategoryModelView.class))
                .toList();
    }

    @Override
    @Transactional
    public void softDeleteCategory(Long categoryId) {
        final TransactionCategory category = findActiveCategoryByIdOrElseThrow(categoryId, categoryRepository);
        category.setStatus(EntityStatus.DELETED);
        category.setParentCategory(null);
        category.setChildCategories(null);

        categoryRepository.save(category);
    }

    private void checkForEmptyCategoryName(final String categoryName) {
        if (StringUtils.isEmpty(categoryName)) {
            logger.error("Название категории не может быть пустым");
            throw new CategoryValidationException("Название категории не может быть пустым");
        }
    }

    private void checkUniqueCategoryName(final String categoryName) {
        if (categoryRepository.existsUserByName(encodeToBase64(categoryName))) {
            final String msg = String.format("Категория %s уже существует", categoryName);
            logger.error(msg);
            throw new CategoryAlreadyExistException(msg);
        }
    }

    static TransactionCategory findActiveCategoryByIdOrElseThrow(final Long categoryId, final TransactionCategoryRepository categoryRepository) {
        return categoryRepository
                .findById(categoryId)
                .filter(category -> category.getStatus().equals(EntityStatus.ACTIVE))
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Категория с id = %s не найдена", categoryId)));
    }

    private TransactionCategory findCategoryByIdOrElseThrow(final Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Категория с id = %s не найдена", categoryId)));
    }

    private void updateCategoryName(final TransactionCategory saved, final TransactionCategory updated) {
        final String currentCategoryName = saved.getName();
        final String newCategoryName = updated.getName().toLowerCase();

        if (!StringUtils.isEmpty(newCategoryName) && !currentCategoryName.equals(newCategoryName))
            saved.setName(newCategoryName);
    }

}
