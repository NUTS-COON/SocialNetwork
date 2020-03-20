package ru.nutscoon.sn.core.model.request;

import javax.validation.constraints.NotEmpty;

public class PersonLoginModel {
    @NotEmpty(message = "Email required")
    private String email;
    @NotEmpty(message = "Password required")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
