package ru.nutscoon.sn.core.model.response;

import java.time.LocalDate;

public class PersonInfoResponse {
    private int id;
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthday;
    private Boolean isMale;


    public PersonInfoResponse(int id, String name, String surname, String phone, LocalDate birthday, Boolean isMale) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.birthday = birthday;
        this.isMale = isMale;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Boolean getIsMale() {
        return isMale;
    }
}
