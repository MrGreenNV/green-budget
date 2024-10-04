package ru.averkiev.budget.views;

import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("unused")
public class FinancialAccountModelView {
    private String accountName;
    private BigDecimal balance;
    private List<TransactionModelView> transactions;

    public FinancialAccountModelView() {
    }

    public FinancialAccountModelView(String accountName, BigDecimal balance, List<TransactionModelView> transactions) {
        this.accountName = accountName;
        this.balance = balance;
        this.transactions = transactions;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<TransactionModelView> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionModelView> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "FinancialAccountModelView{" +
                "accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}
