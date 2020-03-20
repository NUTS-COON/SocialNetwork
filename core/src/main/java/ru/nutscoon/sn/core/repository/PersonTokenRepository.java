package ru.nutscoon.sn.core.repository;

import ru.nutscoon.sn.core.model.entity.PersonToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonTokenRepository extends CrudRepository<PersonToken, Integer> {
    PersonToken getByToken(String token);
    boolean existsByToken(String token);
    void deleteByToken(String token);
    void deleteAllByPersonId(int personId);
}
