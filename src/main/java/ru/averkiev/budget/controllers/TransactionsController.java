package ru.averkiev.budget.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.budget.dto.TransactionDTO;
import ru.averkiev.budget.services.TransactionService;
import ru.averkiev.budget.views.TransactionModelView;

import java.util.List;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/api/v1/transactions")
public class TransactionsController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionModelView> createTransaction(@RequestBody final TransactionDTO transactionDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.createTransaction(transactionDTO));
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionModelView> editTransaction(@PathVariable final Long transactionId,
                                                                @RequestBody final TransactionDTO transactionDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(transactionService.updateTransaction(transactionId, transactionDTO));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionModelView> showInfoTransaction(@PathVariable final Long transactionId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getInfoTransaction(transactionId));
    }

    @GetMapping
    public ResponseEntity<List<TransactionModelView>> showAllTransaction() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getAllTransaction());
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<HttpStatus> deleteTransaction(@PathVariable final Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
