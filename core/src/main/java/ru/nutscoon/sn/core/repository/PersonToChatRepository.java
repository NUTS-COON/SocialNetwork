package ru.nutscoon.sn.core.repository;

import ru.nutscoon.sn.core.model.entity.PersonToChat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PersonToChatRepository extends CrudRepository<PersonToChat, Integer> {
    @Query("select chatId from PersonToChat where person.id = :personId and chatId in (select chatId from PersonToChat where person.id = :participantId)")
    Integer getChatIdByPersonIdAndParticipantId(@Param("personId") int personId, @Param("participantId") int participantId);
    @Query("select ptc from PersonToChat ptc where ptc.person.id <> ?1 and ptc.chatId in (select chatId from PersonToChat where person.id = ?1)")
    Collection<PersonToChat> getByPersonId(int personId);
    Collection<PersonToChat> getByChatId(int chatId);
    boolean existsByChatIdAndPersonId(int chatId, int personId);

//    @Query("")
//    Collection<Chat> getChatByPersonId(int personId);
}
