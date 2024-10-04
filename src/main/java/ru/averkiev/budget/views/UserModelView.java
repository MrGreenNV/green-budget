package ru.averkiev.budget.views;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class UserModelView extends RepresentationModel<UserModelView> {
    private Long id;
    private String username;
    private String email;
    private List<FinancialAccountModelView> accounts;

    public UserModelView() {
    }

    public UserModelView(Long id, String username, String email, List<FinancialAccountModelView> accounts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FinancialAccountModelView> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<FinancialAccountModelView> accounts) {
        this.accounts = accounts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserModelView that = (UserModelView) o;
        return Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, email, accounts);
    }
}
