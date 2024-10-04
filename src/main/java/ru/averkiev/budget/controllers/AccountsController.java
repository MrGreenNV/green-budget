package ru.averkiev.budget.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.budget.dto.FinancialAccountDTO;
import ru.averkiev.budget.services.FinancialAccountService;
import ru.averkiev.budget.views.FinancialAccountModelView;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@SuppressWarnings("unused")
public class AccountsController {

    private final FinancialAccountService accountService;

    @Autowired
    public AccountsController(FinancialAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    public ResponseEntity<FinancialAccountModelView> createAccount(@RequestBody final FinancialAccountDTO financialAccountDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.createAccount(financialAccountDTO));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<FinancialAccountModelView> editAccount(@PathVariable final Long accountId,
                                                                 @RequestBody final FinancialAccountDTO financialAccountDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.updateAccount(accountId, financialAccountDTO));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<FinancialAccountModelView> showAccount(@PathVariable final Long accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getInfoAccount(accountId));
    }

    @GetMapping("/active/{accountId}")
    public ResponseEntity<FinancialAccountModelView> showOnlyActiveAccount(@PathVariable final Long accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getInfoOnlyActiveAccount(accountId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<FinancialAccountModelView>> showAllActiveAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllActiveAccounts());
    }

    @GetMapping()
    public ResponseEntity<List<FinancialAccountModelView>> showAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<HttpStatus> softDeleteAccount(@PathVariable final Long accountId) {
        accountService.softDeleteAccount(accountId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
