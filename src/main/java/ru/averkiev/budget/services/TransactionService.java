package ru.averkiev.budget.services;

import ru.averkiev.budget.dto.TransactionDTO;
import ru.averkiev.budget.views.TransactionModelView;

import java.util.List;

public interface TransactionService {
    TransactionModelView createTransaction(final TransactionDTO transactionDTO);

    TransactionModelView updateTransaction(final Long transactionId, final TransactionDTO transactionDTO);

    TransactionModelView getInfoTransaction(final Long transactionId);

    List<TransactionModelView> getAllTransaction();

    void deleteTransaction(final Long transactionId);
}
