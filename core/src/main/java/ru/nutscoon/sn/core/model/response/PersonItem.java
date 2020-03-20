package ru.nutscoon.sn.core.model.response;

public class PersonItem {
    private int id;
    private String name;


    public PersonItem(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
