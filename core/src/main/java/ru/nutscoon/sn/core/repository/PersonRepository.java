package ru.nutscoon.sn.core.repository;

import ru.nutscoon.sn.core.model.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person getByEmail(String email);
    boolean existsByEmail(String email);

    @Query("select p from Person p where (:name is null or p.name = :name )" +
            "and (:surname is null or p.surname = :surname) " +
            "and (:isMale is null or p.isMale = :isMale)  " +
            "and (:birthday is null or p.birthday = :birthday)")
    Collection<Person> find(
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("isMale") Boolean isMale,
            @Param("birthday") LocalDate birthday);
}
