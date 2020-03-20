package ru.nutscoon.sn;

import ru.nutscoon.sn.SnApplication;
import ru.nutscoon.sn.core.exception.NotFoundException;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.model.entity.PersonToChat;
import ru.nutscoon.sn.core.model.request.SendMessageModel;
import ru.nutscoon.sn.core.model.response.ChatListResponse;
import ru.nutscoon.sn.core.repository.ChatRepository;
import ru.nutscoon.sn.core.repository.PersonRepository;
import ru.nutscoon.sn.core.repository.PersonToChatRepository;
import ru.nutscoon.sn.core.service.ChatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SnApplication.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class CharServiceTest {

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private ChatRepository chatRepository;
    @MockBean
    private PersonToChatRepository personToChatRepository;
    @Autowired
    private ChatService chatService;


    @Test
    public void chatDoesNotExistTest() {
        SendMessageModel model = new SendMessageModel();
        model.setChatId(1);

        assertThrows(NotFoundException.class, () -> chatService.sendMessage(model, 1));
    }

    @Test
    public void getChatsTest() {
        int personId = 1;

        Person person = new Person();
        person.setId(5);
        person.setName("Name");
        person.setSurname("Surname");
        PersonToChat chat = new PersonToChat();
        chat.setChatId(1);
        chat.setPerson(person);

        PersonToChat chat2 = new PersonToChat();
        chat2.setChatId(3);
        chat2.setPerson(new Person());

        PersonToChat chat3 = new PersonToChat();
        chat3.setChatId(3);
        chat3.setPerson(new Person());

        PersonToChat[] chats = new PersonToChat[]{chat, chat2, chat3};

        Mockito.doReturn(true).when(personRepository).existsById(personId);
        Mockito.when(personToChatRepository.getByPersonId(personId)).thenReturn(Arrays.asList(chats));

        ChatListResponse chatListResponse = chatService.getChats(personId);
        assertEquals(chatListResponse.getTotalCount(), chats.length);
        assertTrue(chatListResponse.getChatListItems().stream().anyMatch(c -> c.getChatId() == 1 && c.getPersonId() == 5 && c.getPersonName().equals("Name Surname")));
    }
}
