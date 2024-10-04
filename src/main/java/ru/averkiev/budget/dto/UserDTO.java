package ru.averkiev.budget.dto;

import ru.averkiev.budget.views.FinancialAccountModelView;

import java.util.List;

@SuppressWarnings("unused")
public class UserDTO {
    private String username;
    private String email;
    private List<FinancialAccountModelView> accounts;

    public UserDTO() {
    }

    public UserDTO(final String username, final String email, final List<FinancialAccountModelView> accounts) {
        this.username = username;
        this.email = email;
        this.accounts = accounts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public List<FinancialAccountModelView> getAccounts() {
        return accounts;
    }

    public void setAccounts(final List<FinancialAccountModelView> accounts) {
        this.accounts = accounts;
    }
}
