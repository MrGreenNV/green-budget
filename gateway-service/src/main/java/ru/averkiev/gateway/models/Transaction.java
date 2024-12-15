package ru.averkiev.gateway.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.averkiev.gateway.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transactions")
@SuppressWarnings("unused")
public class Transaction extends BaseEntity {
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "sum")
    private BigDecimal sumOfTransaction;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private TransactionCategory category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_account_id", nullable = false)
    private FinancialAccount financialAccount;

    public Transaction(FinancialAccount financialAccount, TransactionCategory category, BigDecimal sumOfTransaction, TransactionType transactionType) {
        this.financialAccount = financialAccount;
        this.category = category;
        this.sumOfTransaction = sumOfTransaction;
        this.transactionType = transactionType;
    }

    public Transaction(long id, Date createdAt, Date updatedAt, FinancialAccount financialAccount, TransactionCategory category, BigDecimal sumOfTransaction, TransactionType transactionType) {
        super(id, createdAt, updatedAt);
        this.financialAccount = financialAccount;
        this.category = category;
        this.sumOfTransaction = sumOfTransaction;
        this.transactionType = transactionType;
    }

    public Transaction() {
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getSumOfTransaction() {
        return sumOfTransaction;
    }

    public void setSumOfTransaction(BigDecimal sumOfTransaction) {
        this.sumOfTransaction = sumOfTransaction;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public FinancialAccount getFinancialAccount() {
        return financialAccount;
    }

    public void setFinancialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionType == that.transactionType && Objects.equals(sumOfTransaction, that.sumOfTransaction) && Objects.equals(category, that.category) && Objects.equals(financialAccount, that.financialAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionType, sumOfTransaction, category, financialAccount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionType=" + transactionType +
                ", sumOfTransaction=" + sumOfTransaction +
                ", category=" + category +
                ", financialAccount=" + financialAccount +
                '}';
    }
}
