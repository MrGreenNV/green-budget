package ru.averkiev.budget.dto;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class FinancialAccountDTO {
    private String accountName;
    private BigDecimal balance;
    private Long owner;

    public FinancialAccountDTO() {
    }

    public FinancialAccountDTO(final String accountName, final BigDecimal balance, final Long owner) {
        this.accountName = accountName;
        this.balance = balance;
        this.owner = owner;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(final Long owner) {
        this.owner = owner;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(final String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }
}
