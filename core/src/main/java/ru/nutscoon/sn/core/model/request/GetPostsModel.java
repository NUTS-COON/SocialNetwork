package ru.nutscoon.sn.core.model.request;

import javax.validation.constraints.NotNull;

public class GetPostsModel {
    @NotNull
    private Integer publicId;
    private int page;
    private int count;

    public Integer getPublicId() {
        return publicId;
    }

    public void setPublicId(Integer publicId) {
        this.publicId = publicId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
