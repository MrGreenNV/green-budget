package ru.averkiev.gateway.services;

import ru.averkiev.gateway.dto.TransactionDTO;
import ru.averkiev.gateway.views.TransactionModelView;

import java.util.List;

public interface TransactionService {
    TransactionModelView createTransaction(final TransactionDTO transactionDTO);

    TransactionModelView updateTransaction(final Long transactionId, final TransactionDTO transactionDTO);

    TransactionModelView getInfoTransaction(final Long transactionId);

    List<TransactionModelView> getAllTransaction();

    void deleteTransaction(final Long transactionId);
}
