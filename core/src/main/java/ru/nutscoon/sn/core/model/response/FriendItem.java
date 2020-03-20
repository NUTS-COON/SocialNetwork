package ru.nutscoon.sn.core.model.response;

public class FriendItem extends PersonItem {
    private String relation;


    public FriendItem(int id, String name, String relation) {
        super(id, name);
        this.relation = relation;
    }


    public String getRelation() {
        return relation;
    }
}
