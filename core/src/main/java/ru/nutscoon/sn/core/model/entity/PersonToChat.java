package ru.nutscoon.sn.core.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "person_to_chat")
public class PersonToChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @Column(name = "chat_id")
    private int chatId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
