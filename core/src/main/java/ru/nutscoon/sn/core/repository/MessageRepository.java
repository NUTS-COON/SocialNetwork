package ru.nutscoon.sn.core.repository;

import ru.nutscoon.sn.core.model.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    Message getFirst1ByChatIdOrderByDatetimeDesc(int chatId);
    Collection<Message> getByChatId(int chatId);
}
