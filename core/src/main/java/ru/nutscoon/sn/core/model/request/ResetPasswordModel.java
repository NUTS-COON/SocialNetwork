package ru.nutscoon.sn.core.model.request;

import javax.validation.constraints.NotEmpty;

public class ResetPasswordModel {
    @NotEmpty
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
