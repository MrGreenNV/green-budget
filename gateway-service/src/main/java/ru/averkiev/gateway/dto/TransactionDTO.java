package ru.averkiev.gateway.dto;

@SuppressWarnings("unused")
public class TransactionDTO {
    private String transactionType;
    private String sumOfTransaction;
    private Long categoryId;
    private Long accountId;

    public TransactionDTO() {
    }

    public TransactionDTO(final String transactionType, final String sumOfTransaction, final Long categoryId, final Long accountId) {
        this.transactionType = transactionType;
        this.sumOfTransaction = sumOfTransaction;
        this.categoryId = categoryId;
        this.accountId = accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(final String transactionType) {
        this.transactionType = transactionType;
    }

    public String getSumOfTransaction() {
        return sumOfTransaction;
    }

    public void setSumOfTransaction(final String sumOfTransaction) {
        this.sumOfTransaction = sumOfTransaction;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }
}
