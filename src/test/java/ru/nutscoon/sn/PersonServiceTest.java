package ru.nutscoon.sn;

import ru.nutscoon.sn.SnApplication;
import ru.nutscoon.sn.core.exception.NotFoundException;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.model.entity.PublicMessage;
import ru.nutscoon.sn.core.model.response.PersonInfoResponse;
import ru.nutscoon.sn.core.model.response.PublicMessagesResponse;
import ru.nutscoon.sn.core.repository.PersonRepository;
import ru.nutscoon.sn.core.repository.PublicMessageRepository;
import ru.nutscoon.sn.core.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SnApplication.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private PublicMessageRepository publicMessageRepository;
    @Autowired
    private PersonService personService;

    @Test
    public void personInfoTest() {
        int personId = 1;

        Person person = new Person();
        person.setId(personId);
        person.setName("Name");

        Mockito.doReturn(Optional.of(person))
                .when(personRepository)
                .findById(personId);

        PersonInfoResponse personInfo = assertDoesNotThrow(() -> personService.getPersonInfo(personId));
        assertEquals(person.getName(), personInfo.getName());
    }

    @Test
    public void personNotFoundTest() {
        int personId = 1;
        Mockito.doReturn(false).when(personRepository).existsById(personId);

        assertThrows(NotFoundException.class, () -> personService.getPublicMessages(personId));
    }

    @Test
    public void getPublicMessagesTest() {
        int personId = 1;
        PublicMessage[] messages = new PublicMessage[] {
                new PublicMessage(),
                new PublicMessage(),
                new PublicMessage()
        };

        Mockito.doReturn(true).when(personRepository).existsById(personId);
        Mockito.doReturn(Arrays.asList(messages)).when(publicMessageRepository).getByPersonId(personId);

        PublicMessagesResponse response = personService.getPublicMessages(personId);
        assertEquals(response.getMessages().size(), messages.length);
    }
}
