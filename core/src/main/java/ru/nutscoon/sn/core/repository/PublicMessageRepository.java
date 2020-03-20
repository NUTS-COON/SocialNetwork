package ru.nutscoon.sn.core.repository;

import ru.nutscoon.sn.core.model.entity.PublicMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PublicMessageRepository extends CrudRepository<PublicMessage, Integer> {
    Collection<PublicMessage> getByPersonId(int personId);
}
