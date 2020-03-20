package ru.nutscoon.sn.core.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "person_to_public_relation")
public class PersonToPublicRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @Column(name = "public_id")
    private int publicId;
    @Enumerated(EnumType.ORDINAL)
    private PersonToPublicRelationType relation;

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

    public int getPublicId() {
        return publicId;
    }

    public void setPublicId(int publicId) {
        this.publicId = publicId;
    }

    public PersonToPublicRelationType getRelation() {
        return relation;
    }

    public void setRelation(PersonToPublicRelationType relation) {
        this.relation = relation;
    }
}
