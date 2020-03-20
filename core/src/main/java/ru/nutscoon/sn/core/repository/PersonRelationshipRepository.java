package ru.nutscoon.sn.core.repository;

import ru.nutscoon.sn.core.model.entity.PersonRelationship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PersonRelationshipRepository extends CrudRepository<PersonRelationship, Integer> {
    @Query("select r from PersonRelationship r " +
            "where (r.firstPerson.id = :firstPerson and r.secondPerson.id = :secondPerson) " +
            "or (r.firstPerson.id = :secondPerson and r.secondPerson.id = :firstPerson)")
    PersonRelationship find(@Param("firstPerson") int firstPerson, @Param("secondPerson") int secondPerson);

    @Query("select r from PersonRelationship r where r.firstPerson.id = :personId or r.secondPerson.id = :personId")
    Collection<PersonRelationship> findRelationships(@Param("personId") int personId);
}
