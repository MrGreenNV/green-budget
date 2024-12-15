package ru.averkiev.gateway.services.impl;

import io.micrometer.common.util.internal.logging.InternalLogger;
import io.micrometer.common.util.internal.logging.InternalLoggerFactory;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.averkiev.gateway.dto.TransactionDTO;
import ru.averkiev.gateway.enums.EntityStatus;
import ru.averkiev.gateway.enums.TransactionType;
import ru.averkiev.gateway.exceptions.TransactionNotFoundException;
import ru.averkiev.gateway.exceptions.TransactionTypeNotFound;
import ru.averkiev.gateway.models.FinancialAccount;
import ru.averkiev.gateway.models.Transaction;
import ru.averkiev.gateway.models.TransactionCategory;
import ru.averkiev.gateway.repositories.FinancialAccountRepository;
import ru.averkiev.gateway.repositories.TransactionCategoryRepository;
import ru.averkiev.gateway.repositories.TransactionRepository;
import ru.averkiev.gateway.services.TransactionService;
import ru.averkiev.gateway.views.TransactionModelView;

import java.math.BigDecimal;
import java.util.List;

import static ru.averkiev.gateway.services.impl.FinancialAccountServiceImpl.findActiveAccountByIdOrElseThrow;
import static ru.averkiev.gateway.services.impl.TransactionCategoryServiceImpl.findActiveCategoryByIdOrElseThrow;

@Service
@SuppressWarnings("unused")
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionCategoryRepository categoryRepository;
    private final FinancialAccountRepository financialAccountRepository;
    private final ModelMapper modelMapper;
    private final InternalLogger logger = InternalLoggerFactory.getInstance(this.getClass());

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionCategoryRepository categoryRepository, FinancialAccountRepository financialAccountRepository, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.financialAccountRepository = financialAccountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public TransactionModelView createTransaction(TransactionDTO transactionDTO) {
        final Transaction transaction = new Transaction();
        final TransactionType transactionType = TransactionType.fromName(transactionDTO.getTransactionType().toLowerCase());
        final TransactionCategory transactionCategory = findActiveCategoryByIdOrElseThrow(transactionDTO.getCategoryId(), categoryRepository);
        final BigDecimal sumOfTransaction = new BigDecimal(transactionDTO.getSumOfTransaction());
        final FinancialAccount financialAccount = findActiveAccountByIdOrElseThrow(transactionDTO.getAccountId(), financialAccountRepository);

        transaction.setTransactionType(transactionType);
        transaction.setCategory(transactionCategory);
        transaction.setSumOfTransaction(sumOfTransaction);
        transaction.setFinancialAccount(financialAccount);
        transaction.setStatus(EntityStatus.ACTIVE);
        this.makeTransaction(transactionType, financialAccount, sumOfTransaction);

        final Transaction savedTransaction = transactionRepository.save(transaction);
        logger.info("{} по счёту {} на сумму {} в категории {} успешно проведена. Текущий баланс = {}",
                savedTransaction.getTransactionType() == TransactionType.INCOME ? "Пополнение" : "Списание",
                savedTransaction.getFinancialAccount().getAccountName(), savedTransaction.getSumOfTransaction().toString(),
                savedTransaction.getCategory().getName(), financialAccount.getBalance().toString());
        return modelMapper.map(savedTransaction, TransactionModelView.class);
    }

    @Override
    public TransactionModelView updateTransaction(Long transactionId, TransactionDTO transactionDTO) {
        final Transaction savedTransaction = findTransactionByIdOrElseThrow(transactionId);

        this.updateTransactionType(savedTransaction, transactionDTO);
        this.updateTransactionCategory(savedTransaction, transactionDTO);
        this.updateSumOfTransaction(savedTransaction, transactionDTO);
        this.updateFinancialAccount(savedTransaction, transactionDTO);

        final Transaction updatedTransactional = transactionRepository.save(savedTransaction);
        logger.info("Транзакция успешно обновлена");
        return modelMapper.map(updatedTransactional, TransactionModelView.class);
    }

    @Override
    public TransactionModelView getInfoTransaction(Long transactionId) {
        final Transaction transaction = findTransactionByIdOrElseThrow(transactionId);
        return modelMapper.map(transaction, TransactionModelView.class);
    }

    @Override
    public List<TransactionModelView> getAllTransaction() {
        return transactionRepository
                .findAll().stream()
                .map(transaction -> modelMapper.map(transaction, TransactionModelView.class))
                .toList();
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        final Transaction transaction = findTransactionByIdOrElseThrow(transactionId);
        transactionRepository.delete(transaction);
        logger.info("Транзакция с id = {} успешно удалена.");
    }

    private void updateTransactionType(final Transaction saved, final TransactionDTO updated) {
        final String transactionTypeName = updated.getTransactionType().toLowerCase();
        if (!StringUtils.isEmpty(transactionTypeName) && !saved.getTransactionType().name().equalsIgnoreCase(transactionTypeName))
            saved.setTransactionType(TransactionType.fromName(transactionTypeName));
    }

    private void updateTransactionCategory(final Transaction saved, final TransactionDTO updated) {
        final Long categoryId = updated.getCategoryId();
        if (categoryId != null && saved.getCategory().getId() != categoryId) {
            final TransactionCategory transactionCategory = findActiveCategoryByIdOrElseThrow(categoryId, categoryRepository);
            saved.setCategory(transactionCategory);
        }
    }

    private void updateSumOfTransaction(final Transaction saved, final TransactionDTO updated) {
        final String sumOfTransaction = updated.getSumOfTransaction();

        if (!StringUtils.isEmpty(sumOfTransaction))
            return;

        final BigDecimal sum = new BigDecimal(sumOfTransaction);
        saved.setSumOfTransaction(sum);
    }

    private void updateFinancialAccount(final Transaction saved, final TransactionDTO updated) {
        final Long accountId = updated.getAccountId();
        if (accountId != null && saved.getFinancialAccount().getId() != accountId) {
            final FinancialAccount financialAccount = findActiveAccountByIdOrElseThrow(accountId, financialAccountRepository);
            saved.setFinancialAccount(financialAccount);
        }
    }

    private void makeTransaction(final TransactionType transactionType, final FinancialAccount financialAccount,
                                 final BigDecimal sumOfTransaction) {

        if (transactionType == TransactionType.EXPENSE) {
            financialAccount.setBalance(financialAccount.getBalance().subtract(sumOfTransaction));
        } else if (transactionType == TransactionType.INCOME) {
            financialAccount.setBalance(financialAccount.getBalance().add(sumOfTransaction));
        } else {
            throw new TransactionTypeNotFound("Неизвестный тип транзакции");
        }
    }

    private Transaction findTransactionByIdOrElseThrow(final Long transactionId) {
        return transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(String.format("Транзакция с id = %s не найдена", transactionId)));
    }
}
