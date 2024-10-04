package ru.averkiev.budget.views;

@SuppressWarnings("unused")
public class TransactionModelView {
    private String transactionType;
    private String sumOfTransaction;
    private String categoryName;
    private String financialAccount;
    private String updatedAt;

    public TransactionModelView() {
    }

    public TransactionModelView(String transactionType, String sumOfTransaction, String categoryName, String financialAccount, String updatedAt) {
        this.transactionType = transactionType;
        this.sumOfTransaction = sumOfTransaction;
        this.categoryName = categoryName;
        this.financialAccount = financialAccount;
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getSumOfTransaction() {
        return sumOfTransaction;
    }

    public void setSumOfTransaction(String sumOfTransaction) {
        this.sumOfTransaction = sumOfTransaction;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFinancialAccount() {
        return financialAccount;
    }

    public void setFinancialAccount(String financialAccount) {
        this.financialAccount = financialAccount;
    }

    @Override
    public String toString() {
        return "TransactionModelView{" +
                "transactionType=" + transactionType +
                ", sumOfTransaction=" + sumOfTransaction +
                ", category=" + categoryName +
                ", financialAccount=" + financialAccount +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
