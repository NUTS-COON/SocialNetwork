package ru.nutscoon.sn.core.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SendPostModel {
    @NotNull(message = "{sendPost.required.publicId}")
    private Integer publicId;
    @NotEmpty(message = "{sendPost.required.text}")
    private String text;

    public Integer getPublicId() {
        return publicId;
    }

    public void setPublicId(Integer publicId) {
        this.publicId = publicId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
