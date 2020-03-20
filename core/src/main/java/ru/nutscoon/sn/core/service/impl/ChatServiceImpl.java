package ru.nutscoon.sn.core.service.impl;

import ru.nutscoon.sn.core.exception.AccessDeniedException;
import ru.nutscoon.sn.core.exception.InvalidModelException;
import ru.nutscoon.sn.core.exception.InvalidOperationException;
import ru.nutscoon.sn.core.exception.NotFoundException;
import ru.nutscoon.sn.core.model.entity.Chat;
import ru.nutscoon.sn.core.model.entity.Message;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.model.entity.PersonToChat;
import ru.nutscoon.sn.core.model.request.SendMessageModel;
import ru.nutscoon.sn.core.model.response.ChatListResponse;
import ru.nutscoon.sn.core.model.response.ChatMessagesResponse;
import ru.nutscoon.sn.core.repository.*;
import ru.nutscoon.sn.core.service.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl extends BaseService implements ChatService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final PersonToChatRepository personToChatRepository;
    private final PersonRepository personRepository;

    @Value("${maxMessageLength}")
    private int maxMessageLength;

    public ChatServiceImpl(
            PersonRepository personRepository,
            MessageRepository messageRepository,
            ChatRepository chatRepository,
            PersonToChatRepository personToChatRepository,
            PersonRepository personRepository1) {
        super(personRepository);
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.personToChatRepository = personToChatRepository;
        this.personRepository = personRepository1;
    }


    @Override
    public int createChat(int currentPersonId, int personId) {
        if (!personRepository.existsById(personId)) {
            throw new NotFoundException("Person not found");
        }

        if (currentPersonId == personId) {
            throw new InvalidOperationException("Invalid operation");
        }
        Integer chatId = personToChatRepository.getChatIdByPersonIdAndParticipantId(currentPersonId, personId);
        if (chatId != null) {
            throw new InvalidOperationException("Chat already exists");
        }

        Chat chat = new Chat();
        chatId = chatRepository.save(chat).getId();
        createPersonToChatRelation(chatId, currentPersonId);
        createPersonToChatRelation(chatId, personId);

        return chatId;
    }

    @Override
    public void sendMessage(SendMessageModel model, int personId) {
        Person person = getPerson(personId);
        if (!personToChatRepository.existsByChatIdAndPersonId(model.getChatId(), person.getId())) {
            throw new NotFoundException("Chat does not exist");
        }

        if (model.getMessage().length() > maxMessageLength) {
            throw new InvalidModelException("Message length limit is" + maxMessageLength);
        }

        Message message = new Message();
        message.setSender(person);
        message.setText(model.getMessage());
        message.setChatId(model.getChatId());
        message.setDatetime(LocalDateTime.now());

        messageRepository.save(message);
    }

    @Override
    public ChatListResponse getChats(int personId) {
        if (!personRepository.existsById(personId)) {
            throw new NotFoundException("Person not found");
        }

        Collection<PersonToChat> chats = personToChatRepository.getByPersonId(personId);

        Collection<ChatListResponse.ChatListItem> items = chats.stream()
                .map(chat -> new ChatListResponse.ChatListItem(
                        chat.getChatId(),
                        chat.getPerson().getId(),
                        chat.getPerson().getFullName()))
                .collect(Collectors.toList());
        return new ChatListResponse(chats.size(), items);
    }

    @Override
    public ChatMessagesResponse getChatMessages(int personId, int chatId) {
        if (!personRepository.existsById(personId)) {
            throw new NotFoundException("Person not found");
        }

        if(!chatRepository.existsById(chatId)){
            throw new NotFoundException("Chat not found");
        }

        if(!personToChatRepository.existsByChatIdAndPersonId(chatId, personId)){
            throw new AccessDeniedException();
        }

        PersonToChat personToChat = personToChatRepository.getByChatId(chatId)
                .stream().filter(chat -> chat.getPerson().getId() != personId)
                .findFirst().orElseGet(PersonToChat::new);
        Collection<Message> messages = messageRepository.getByChatId(chatId);

        return new ChatMessagesResponse(
                messages.size(),
                personToChat.getPerson().getId(),
                personToChat.getPerson().getFullName(),
                messages.stream()
                        .map(x -> new ChatMessagesResponse.ChatMessageItem(x.getSender().getId(), x.getText(), x.getDatetime()))
                        .collect(Collectors.toList())
        );
    }

    private void createPersonToChatRelation(int chatId, int personId) {
        Person person = new Person();
        person.setId(personId);

        PersonToChat personToChat = new PersonToChat();
        personToChat.setChatId(chatId);
        personToChat.setPerson(person);

        personToChatRepository.save(personToChat);
    }
}
