package ru.nutscoon.sn.core.model.request;

import javax.validation.constraints.NotEmpty;

public class PersonRegisterModel {

    @NotEmpty(message = "{register.required.email}")
    private String email;
    @NotEmpty(message = "{register.required.password}")
    private String password;
    @NotEmpty(message = "{register.required.name}")
    private String name;
    @NotEmpty(message = "{register.required.surname}")
    private String surname;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
