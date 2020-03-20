package ru.nutscoon.sn.core.model.request;

import javax.validation.constraints.NotNull;

public class UpdatePublicModel {
    @NotNull(message = "{updatePublic.required.id}")
    private Integer id;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
