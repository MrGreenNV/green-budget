package ru.averkiev.budget.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static ru.averkiev.budget.utils.Utils.decodeFromBase64;
import static ru.averkiev.budget.utils.Utils.encodeToBase64;

@Entity
@Table(name = "users")
@SuppressWarnings("unused")
public class User extends BaseEntity {
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<FinancialAccount> accounts;

    public User(String username, String email, String password, List<FinancialAccount> accounts) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.accounts = accounts;
    }

    public User(long id, Date createdAt, Date updatedAt, String username, String email, String password, List<FinancialAccount> accounts) {
        super(id, createdAt, updatedAt);
        this.username = username;
        this.email = email;
        this.password = password;
        this.accounts = accounts;
    }

    public User() {
    }

    public String getUsername() {
        if (StringUtils.isEmpty(username))
            return username;
        return decodeFromBase64(username);
    }

    public void setUsername(String username) {
        if (!StringUtils.isEmpty(username))
            this.username = encodeToBase64(username);
        else
            this.username = username;
    }

    public String getEmail() {
        if (StringUtils.isEmpty(email))
            return email;
        return decodeFromBase64(email);
    }

    public void setEmail(String email) {
        if (!StringUtils.isEmpty(email))
            this.email = encodeToBase64(email);
        else
            this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<FinancialAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<FinancialAccount> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(accounts, user.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, accounts);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", financialAccounts=" + accounts +
                '}';
    }
}
