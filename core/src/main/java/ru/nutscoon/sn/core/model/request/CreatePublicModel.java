package ru.nutscoon.sn.core.model.request;

import javax.validation.constraints.NotEmpty;

public class CreatePublicModel {
    @NotEmpty(message = "{createPublic.required.name}")
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
