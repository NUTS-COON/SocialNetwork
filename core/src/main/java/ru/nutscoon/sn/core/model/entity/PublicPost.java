package ru.nutscoon.sn.core.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "public_post")
public class PublicPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "public_id")
    private int publicId;
    @ManyToOne
    @JoinColumn(name = "author")
    private Person author;
    private String text;
    @Column(name = "is_published")
    private boolean isPublished;
    private LocalDateTime published;
    private LocalDateTime created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicId() {
        return publicId;
    }

    public void setPublicId(int publicId) {
        this.publicId = publicId;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public LocalDateTime getPublished() {
        return published;
    }

    public void setPublished(LocalDateTime published) {
        this.published = published;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
