package ru.nutscoon.sn.core.model.request;

import javax.validation.constraints.NotNull;

public class MakePublicAdminModel {
    @NotNull
    private Integer publicId;
    @NotNull
    private Integer personId;
    @NotNull
    private Boolean isAdmin;

    public Integer getPublicId() {
        return publicId;
    }

    public void setPublicId(Integer publicId) {
        this.publicId = publicId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Boolean IsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
