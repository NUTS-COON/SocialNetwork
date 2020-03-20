package ru.nutscoon.sn.core.model.request;

import java.time.LocalDate;

public class PersonUpdateModel {
    private String phone;
    private Boolean isMale;
    private LocalDate birthday;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean isMale) {
        this.isMale = isMale;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
