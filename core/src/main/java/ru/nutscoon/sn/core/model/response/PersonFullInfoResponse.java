package ru.nutscoon.sn.core.model.response;

import java.time.LocalDate;

public class PersonFullInfoResponse extends PersonInfoResponse {

    private int id;
    private String email;

    public PersonFullInfoResponse(
            int id,
            String email,
            String name,
            String surname,
            String phone,
            LocalDate birthday,
            Boolean isMale) {
        super(id, name, surname, phone, birthday, isMale);
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
