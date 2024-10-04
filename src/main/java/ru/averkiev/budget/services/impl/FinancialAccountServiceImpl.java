package ru.averkiev.budget.services.impl;

import io.micrometer.common.util.internal.logging.InternalLogger;
import io.micrometer.common.util.internal.logging.InternalLoggerFactory;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.averkiev.budget.dto.FinancialAccountDTO;
import ru.averkiev.budget.enums.EntityStatus;
import ru.averkiev.budget.exceptions.AccountNotFoundException;
import ru.averkiev.budget.exceptions.EmailAlreadyExistException;
import ru.averkiev.budget.models.FinancialAccount;
import ru.averkiev.budget.models.User;
import ru.averkiev.budget.repositories.FinancialAccountRepository;
import ru.averkiev.budget.repositories.UserRepository;
import ru.averkiev.budget.services.FinancialAccountService;
import ru.averkiev.budget.services.UserService;
import ru.averkiev.budget.views.FinancialAccountModelView;

import java.util.List;

@Service
@SuppressWarnings("unused")
public class FinancialAccountServiceImpl implements FinancialAccountService {
    private final FinancialAccountRepository financialAccountRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final InternalLogger logger = InternalLoggerFactory.getInstance(this.getClass());

    @Autowired
    public FinancialAccountServiceImpl(FinancialAccountRepository financialAccountRepository, UserRepository userRepository, ModelMapper modelMapper, UserService userService1) {
        this.financialAccountRepository = financialAccountRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public FinancialAccountModelView createAccount(final FinancialAccountDTO financialAccountDTO) {
        checkForEmptyAccountName(financialAccountDTO.getAccountName());

        final User user = UserServiceImpl.findActiveUserByIdOrElseThrow(financialAccountDTO.getOwner(), userRepository);
        final FinancialAccount financialAccount = modelMapper.map(financialAccountDTO, FinancialAccount.class);
        financialAccount.setStatus(EntityStatus.ACTIVE);
        financialAccount.setUser(user);

        final FinancialAccount saved = financialAccountRepository.save(financialAccount);
        logger.info("Счёт {} успешно сохранён", saved.getAccountName());
        return modelMapper.map(saved, FinancialAccountModelView.class);
    }

    @Override
    public FinancialAccountModelView updateAccount(final Long accountId, final FinancialAccountDTO financialAccountDTO) {
        final FinancialAccount savedAccount = findActiveAccountByIdOrElseThrow(accountId, financialAccountRepository);
        final FinancialAccount updatedAccount = modelMapper.map(financialAccountDTO, FinancialAccount.class);

        updateAccountName(savedAccount, updatedAccount);

        return modelMapper.map(financialAccountRepository.save(savedAccount), FinancialAccountModelView.class);
    }

    @Override
    public FinancialAccountModelView getInfoAccount(final Long accountId) {
        final FinancialAccount findAccount = this.findAccountByIdOrElseThrow(accountId);
        return modelMapper.map(findAccount, FinancialAccountModelView.class);
    }

    @Override
    public FinancialAccountModelView getInfoOnlyActiveAccount(final Long accountId) {
        final FinancialAccount findAccount = findActiveAccountByIdOrElseThrow(accountId, financialAccountRepository);
        return modelMapper.map(findAccount, FinancialAccountModelView.class);
    }

    @Override
    public List<FinancialAccountModelView> getAllAccounts() {
        return financialAccountRepository.findAll().stream()
                .map(s -> modelMapper.map(s, FinancialAccountModelView.class))
                .toList();
    }

    @Override
    public List<FinancialAccountModelView> getAllActiveAccounts() {
        return financialAccountRepository.findAll().stream()
                .filter(account -> account.getStatus().equals(EntityStatus.ACTIVE))
                .map(s -> modelMapper.map(s, FinancialAccountModelView.class))
                .toList();
    }

    @Override
    public void softDeleteAccount(final Long accountId) {
        final FinancialAccount findAccount = findActiveAccountByIdOrElseThrow(accountId, financialAccountRepository);
        findAccount.setStatus(EntityStatus.DELETED);
        financialAccountRepository.save(findAccount);
    }

    private FinancialAccount findAccountByIdOrElseThrow(final Long accountId) {
        return financialAccountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(String.format("Счёт с id = %d не найден", accountId)));
    }

    static FinancialAccount findActiveAccountByIdOrElseThrow(final Long accountId, final FinancialAccountRepository financialAccountRepository) {
        return financialAccountRepository.findById(accountId)
                .filter(account -> account.getStatus().equals(EntityStatus.ACTIVE))
                .orElseThrow(() -> new AccountNotFoundException(String.format("Счёт с id = %d не найден", accountId)));
    }

    private void checkForEmptyAccountName(final String accountName) {
        if (StringUtils.isEmpty(accountName)) {
            logger.error("Название счёта не может быть пустым");
            throw new EmailAlreadyExistException("Название счёта не может быть пустым");
        }
    }

    private void updateAccountName(final FinancialAccount saved, final FinancialAccount updated) {
        final String currentAccountName = saved.getAccountName();
        final String newAccountName = updated.getAccountName();
        if (!StringUtils.isEmpty(newAccountName) && !currentAccountName.equals(newAccountName))
            saved.setAccountName(newAccountName);
    }
}
