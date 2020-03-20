package ru.nutscoon.sn;

import ru.nutscoon.sn.SnApplication;
import ru.nutscoon.sn.core.exception.AccessDeniedException;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.model.entity.PersonToPublicRelation;
import ru.nutscoon.sn.core.model.entity.PersonToPublicRelationType;
import ru.nutscoon.sn.core.model.entity.PublicPost;
import ru.nutscoon.sn.core.model.request.SendPostModel;
import ru.nutscoon.sn.core.repository.PersonRepository;
import ru.nutscoon.sn.core.repository.PersonToPublicRelationRepository;
import ru.nutscoon.sn.core.repository.PublicPostRepository;
import ru.nutscoon.sn.core.service.PublicPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = SnApplication.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class PublicPostServiceTest {

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private PersonToPublicRelationRepository personToPublicRelationRepository;
    @MockBean
    private PublicPostRepository publicPostRepository;
    @Autowired
    private PublicPostService publicPostService;

    public void canSendPostIfSubscriber() {
        int personId = 1;
        int publicId = 2;
        Person person = new Person();
        person.setId(personId);
        Mockito.doReturn(Optional.of(person)).when(personRepository).findById(personId);

        SendPostModel model = new SendPostModel();
        model.setPublicId(2);

        PersonToPublicRelation personToPublicRelation = new PersonToPublicRelation();
        personToPublicRelation.setPerson(person);
        personToPublicRelation.setRelation(PersonToPublicRelationType.SUBSCRIBER);
        Mockito.doReturn(personToPublicRelation).when(personToPublicRelationRepository).getByPublicIdAndPersonId(publicId, personId);
        Mockito.doReturn(true).when(personRepository).existsById(personId);

        assertDoesNotThrow(() -> publicPostService.sendPost(model, personId));
    }

    @Test
    public void canNotPublishPostIfNotAdminOrOwner(){
        int personId = 1;
        int publicId = 2;
        int postId = 3;

        PublicPost post = new PublicPost();
        post.setPublicId(publicId);

        Mockito.doReturn(Optional.of(post)).when(publicPostRepository).findById(postId);
        Mockito.doReturn(true).when(personRepository).existsById(personId);
        PersonToPublicRelation personToPublicRelation = new PersonToPublicRelation();
        personToPublicRelation.setRelation(PersonToPublicRelationType.SUBSCRIBER);
        Mockito.doReturn(personToPublicRelation).when(personToPublicRelationRepository).getByPublicIdAndPersonId(publicId, personId);

        assertThrows(AccessDeniedException.class, () -> publicPostService.publishPost(personId, postId));
    }
}
