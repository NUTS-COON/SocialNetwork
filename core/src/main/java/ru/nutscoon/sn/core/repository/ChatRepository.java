package ru.nutscoon.sn.core.repository;

import ru.nutscoon.sn.core.model.entity.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Integer> {
}
