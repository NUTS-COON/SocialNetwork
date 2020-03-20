package ru.nutscoon.sn.core.model.response;

import java.util.Collection;

public class FindPublicResponse {
    private int count;
    private Collection<FindPublicItem> items;


    public FindPublicResponse(int count, Collection<FindPublicItem> items) {
        this.count = count;
        this.items = items;
    }


    public int getCount() {
        return count;
    }

    public Collection<FindPublicItem> getItems() {
        return items;
    }



    public static class FindPublicItem{
        private int publicId;
        private String name;
        private String description;

        public FindPublicItem(int publicId, String name, String description) {
            this.publicId = publicId;
            this.name = name;
            this.description = description;
        }

        public int getPublicId() {
            return publicId;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }
}
