package ru.nutscoon.sn.core.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "person_relationship")
public class PersonRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "first_person")
    private Person firstPerson;
    @ManyToOne
    @JoinColumn(name = "second_person")
    private Person secondPerson;
    @Enumerated(EnumType.ORDINAL)
    private PersonRelationshipType relationship;
    @ManyToOne
    @JoinColumn(name = "last_initiator")
    private Person lastInitiator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getFirstPerson() {
        return firstPerson;
    }

    public void setFirstPerson(Person firstPerson) {
        this.firstPerson = firstPerson;
    }

    public Person getSecondPerson() {
        return secondPerson;
    }

    public void setSecondPerson(Person secondPerson) {
        this.secondPerson = secondPerson;
    }

    public PersonRelationshipType getRelationship() {
        return relationship;
    }

    public void setRelationship(PersonRelationshipType relationship) {
        this.relationship = relationship;
    }

    public Person getLastInitiator() {
        return lastInitiator;
    }

    public void setLastInitiator(Person lastInitiator) {
        this.lastInitiator = lastInitiator;
    }
}
