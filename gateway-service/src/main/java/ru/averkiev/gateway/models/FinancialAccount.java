package ru.averkiev.gateway.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static ru.averkiev.gateway.utils.Utils.decodeFromBase64;
import static ru.averkiev.gateway.utils.Utils.encodeToBase64;

@Entity
@Table(name = "financial_accounts")
@SuppressWarnings("unused")
public class FinancialAccount extends BaseEntity {
    @Column(name = "account_name")
    private String accountName;

    @Column(name = "balance")
    private BigDecimal balance;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private User user;

    @OneToMany(mappedBy = "financialAccount")
    private List<Transaction> transactions;

    public FinancialAccount(String accountName, BigDecimal balance, User user, List<Transaction> transactions) {
        this.accountName = accountName;
        this.balance = balance;
        this.user = user;
        this.transactions = transactions;
    }

    public FinancialAccount(long id, Date createdAt, Date updatedAt, String accountName, BigDecimal balance, User user, List<Transaction> transactions) {
        super(id, createdAt, updatedAt);
        this.accountName = accountName;
        this.balance = balance;
        this.user = user;
        this.transactions = transactions;
    }

    public FinancialAccount(){
    }

    public String getAccountName() {
        if (StringUtils.isEmpty(accountName))
            return accountName;
        return decodeFromBase64(accountName);
    }

    public void setAccountName(String accountName) {
        if (!StringUtils.isEmpty(accountName))
            this.accountName = encodeToBase64(accountName);
        else
            this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialAccount that = (FinancialAccount) o;
        return Objects.equals(accountName, that.accountName) && Objects.equals(balance, that.balance) && Objects.equals(user, that.user) && Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, balance, user, transactions);
    }

    @Override
    public String toString() {
        return "FinancialAccount{" +
                "accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}
