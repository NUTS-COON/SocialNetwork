package ru.nutscoon.sn.core.model.request;

import javax.validation.constraints.NotEmpty;

public class CreatePublicMessageModel {

    @NotEmpty(message = "{sendPublicMessage.required.message}")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
