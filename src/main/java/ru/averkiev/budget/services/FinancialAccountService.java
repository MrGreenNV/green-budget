package ru.averkiev.budget.services;

import ru.averkiev.budget.dto.FinancialAccountDTO;
import ru.averkiev.budget.views.FinancialAccountModelView;

import java.util.List;

public interface FinancialAccountService {
    FinancialAccountModelView createAccount(final FinancialAccountDTO financialAccountDTO);

    FinancialAccountModelView updateAccount(final Long accountId, final FinancialAccountDTO financialAccountDTO);

    FinancialAccountModelView getInfoAccount(final Long accountId);

    FinancialAccountModelView getInfoOnlyActiveAccount(final Long accountId);

    List<FinancialAccountModelView> getAllAccounts();

    List<FinancialAccountModelView> getAllActiveAccounts();

    void softDeleteAccount(final Long accountId);
}
