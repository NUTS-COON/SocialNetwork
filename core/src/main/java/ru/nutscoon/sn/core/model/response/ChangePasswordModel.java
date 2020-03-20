package ru.nutscoon.sn.core.model.response;

import javax.validation.constraints.NotEmpty;

public class ChangePasswordModel {
    @NotEmpty
    private String email;
    @NotEmpty
    private String currentPassword;
    @NotEmpty
    private String newPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
