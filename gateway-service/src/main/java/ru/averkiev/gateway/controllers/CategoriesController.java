package ru.averkiev.gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.gateway.dto.TransactionCategoryDTO;
import ru.averkiev.gateway.services.TransactionCategoryService;
import ru.averkiev.gateway.views.TransactionCategoryModelView;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@SuppressWarnings("unused")
public class CategoriesController {
    private final TransactionCategoryService categoryService;

    @Autowired
    public CategoriesController(TransactionCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<TransactionCategoryModelView> createCategory(@RequestBody final TransactionCategoryDTO transactionCategoryDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.createCategory(transactionCategoryDTO));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<TransactionCategoryModelView> editCategory(@PathVariable final Long categoryId,
                                                                     @RequestBody final TransactionCategoryDTO transactionCategoryDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(categoryId, transactionCategoryDTO));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<TransactionCategoryModelView> showCategory(@PathVariable final Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getInfoCategory(categoryId));
    }

    @GetMapping("/active/{categoryId}")
    public ResponseEntity<TransactionCategoryModelView> showOnlyActiveCategory(@PathVariable final Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getInfoActiveCategory(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<TransactionCategoryModelView>> showAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories());
    }

    @GetMapping("/active")
    public ResponseEntity<List<TransactionCategoryModelView>> showAllOnlyActiveCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllActiveCategories());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<HttpStatus> softDeleteCategory(@PathVariable final Long categoryId) {
        categoryService.softDeleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

