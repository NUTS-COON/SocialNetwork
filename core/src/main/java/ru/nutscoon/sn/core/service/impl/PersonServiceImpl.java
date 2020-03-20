package ru.nutscoon.sn.core.service.impl;

import ru.nutscoon.sn.core.exception.InvalidOperationException;
import ru.nutscoon.sn.core.exception.NotFoundException;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.model.entity.PersonRelationship;
import ru.nutscoon.sn.core.model.entity.PersonRelationshipType;
import ru.nutscoon.sn.core.model.entity.PublicMessage;
import ru.nutscoon.sn.core.model.request.CreatePublicMessageModel;
import ru.nutscoon.sn.core.model.request.FindPersonRequest;
import ru.nutscoon.sn.core.model.request.PersonUpdateModel;
import ru.nutscoon.sn.core.model.response.FriendItem;
import ru.nutscoon.sn.core.model.response.PersonFullInfoResponse;
import ru.nutscoon.sn.core.model.response.PersonInfoResponse;
import ru.nutscoon.sn.core.model.response.PublicMessagesResponse;
import ru.nutscoon.sn.core.repository.PersonRelationshipRepository;
import ru.nutscoon.sn.core.repository.PersonRepository;
import ru.nutscoon.sn.core.repository.PublicMessageRepository;
import ru.nutscoon.sn.core.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl extends BaseService implements PersonService {

    private final PersonRepository personRepository;
    private final PublicMessageRepository publicMessageRepository;
    private final PersonRelationshipRepository personRelationshipRepository;


    @Autowired
    public PersonServiceImpl(
            PersonRepository personRepository,
            PublicMessageRepository publicMessageRepository,
            PersonRelationshipRepository personRelationshipRepository) {
        super(personRepository);
        this.personRepository = personRepository;
        this.publicMessageRepository = publicMessageRepository;
        this.personRelationshipRepository = personRelationshipRepository;
    }

    public void update(PersonUpdateModel model, int personId) {
        Person person = getPerson(personId);
        person.setPhone(model.getPhone());
        person.setIsMale(model.getIsMale());
        person.setBirthday(model.getBirthday());

        personRepository.save(person);
    }

    @Override
    public void createPublicMessage(CreatePublicMessageModel model, int personId) {
        if (!personRepository.existsById(personId)) {
            throw new NotFoundException("Person not found");
        }

        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setPersonId(personId);
        publicMessage.setText(model.getMessage());
        publicMessage.setDate(LocalDateTime.now());

        publicMessageRepository.save(publicMessage);
    }

    @Override
    public PublicMessagesResponse getPublicMessages(int personId) {
        if (!personRepository.existsById(personId)) {
            throw new NotFoundException("Person not found");
        }

        Collection<PublicMessage> publicMessages = publicMessageRepository.getByPersonId(personId);
        if (publicMessages == null || publicMessages.isEmpty()) {
            return new PublicMessagesResponse(personId, new ArrayList<>());
        }

        return new PublicMessagesResponse(personId, getPublicMessageItems(publicMessages));
    }

    @Override
    public PersonFullInfoResponse getPersonFullInfo(int personId) {
        Person person = getPerson(personId);
        return new PersonFullInfoResponse(
                person.getId(),
                person.getEmail(),
                person.getName(),
                person.getSurname(),
                person.getPhone(),
                person.getBirthday(),
                person.getIsMale()
        );
    }

    @Override
    public PersonInfoResponse getPersonInfo(int personId) {
        return getPersonInfoResponse(getPerson(personId));
    }

    @Override
    public Collection<PersonInfoResponse> findPerson(FindPersonRequest model) {
        return personRepository.find(model.getName(), model.getSurname(), model.getIsMale(), model.getBirthday())
                .stream().map(this::getPersonInfoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void addFriend(int currentPersonId, int personId) {
        Person firstPerson = getPerson(currentPersonId);
        PersonRelationship relationship = personRelationshipRepository.find(firstPerson.getId(), personId);
        if (relationship == null) {
            Person secondPerson = new Person();
            secondPerson.setId(personId);
            addToFriend(firstPerson, secondPerson);
            return;
        }

        switch (relationship.getRelationship()) {
            case REQUEST:
                if (relationship.getLastInitiator().getId() == personId) {
                    relationship.setRelationship(PersonRelationshipType.CONFIRM);
                    relationship.setLastInitiator(firstPerson);
                    personRelationshipRepository.save(relationship);
                } else {
                    throw new InvalidOperationException("Request already made");
                }
                break;
            case CONFIRM:
                throw new InvalidOperationException("Friendship already confirmed");
            case CANCEL:
                if (relationship.getLastInitiator().getId() == personId) {
                    throw new InvalidOperationException("Request already made");
                }
                throw new RuntimeException("Invalid relationship state");
        }
    }

    @Override
    @Transactional
    public void deleteFriend(int currentPersonId, int personId) {
        if (!personRepository.existsById(personId)) {
            throw new NotFoundException("Person not found");
        }

        Person firstPerson = getPerson(currentPersonId);
        PersonRelationship relationship = personRelationshipRepository.find(firstPerson.getId(), personId);
        if (relationship == null) {
            return;
        }

        switch (relationship.getRelationship()) {
            case REQUEST:
                if (relationship.getLastInitiator().getId() == personId) {
                    throw new InvalidOperationException("Not friend");
                }

                personRelationshipRepository.delete(relationship);
                break;
            case CONFIRM:
                relationship.setRelationship(PersonRelationshipType.CANCEL);
                relationship.setLastInitiator(firstPerson);
                personRelationshipRepository.save(relationship);
                break;
            case CANCEL:
                if (relationship.getLastInitiator().getId() == personId) {
                    personRelationshipRepository.delete(relationship);
                } else {
                    throw new RuntimeException("Already canceled");
                }
        }
    }

    @Override
    public Collection<FriendItem> getFriends(int personId) {
        if (!personRepository.existsById(personId)) {
            throw new NotFoundException("Person not found");
        }

        return personRelationshipRepository
                .findRelationships(personId).stream()
                .map(x -> getFriendItem(personId, x))
                .collect(Collectors.toList());
    }

    private void addToFriend(Person firstPerson, Person secondPerson) {
        PersonRelationship relationship = new PersonRelationship();
        relationship.setFirstPerson(firstPerson);
        relationship.setSecondPerson(secondPerson);
        relationship.setLastInitiator(firstPerson);
        relationship.setRelationship(PersonRelationshipType.REQUEST);

        personRelationshipRepository.save(relationship);
    }

    private FriendItem getFriendItem(int personId, PersonRelationship relationship) {
        Person friend = findOutFriend(personId, relationship);
        return new FriendItem(friend.getId(), friend.getFullName(), getRelationshipStatus(personId, relationship));
    }

    private String getRelationshipStatus(int personId, PersonRelationship relationship) {
        switch (relationship.getRelationship()) {
            case REQUEST:
                if (relationship.getLastInitiator().getId() == personId) {
                    return "Request sent";
                }

                return "Request received";
            case CONFIRM:
                return "Friend";
            case CANCEL:
                if (relationship.getLastInitiator().getId() == personId) {
                    return "Canceled";
                }

                return "Rejected";
        }

        return "Unknown";
    }

    private Person findOutFriend(int personId, PersonRelationship relationship) {
        if (relationship.getFirstPerson().getId() == personId) {
            return relationship.getSecondPerson();
        }

        return relationship.getFirstPerson();
    }

    private Collection<PublicMessagesResponse.PublicMessagesResponseItem> getPublicMessageItems(Collection<PublicMessage> publicMessages) {
        return publicMessages
                .stream()
                .map(x -> new PublicMessagesResponse.PublicMessagesResponseItem(x.getId(), x.getText(), x.getDate()))
                .collect(Collectors.toList());
    }

    private PersonInfoResponse getPersonInfoResponse(Person person) {
        return new PersonInfoResponse(
                person.getId(),
                person.getName(),
                person.getSurname(),
                person.getPhone(),
                person.getBirthday(),
                person.getIsMale()
        );
    }
}
