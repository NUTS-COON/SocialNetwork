package ru.nutscoon.sn.api.controller;

import ru.nutscoon.sn.core.model.request.CreatePublicMessageModel;
import ru.nutscoon.sn.core.model.request.FindPersonRequest;
import ru.nutscoon.sn.core.model.request.PersonUpdateModel;
import ru.nutscoon.sn.core.model.response.*;
import ru.nutscoon.sn.api.service.PersonAuthService;
import ru.nutscoon.sn.core.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("api/person")
public class PersonController {

    private final PersonService personService;
    private final PersonAuthService personAuthService;


    @Autowired
    public PersonController(PersonService personService, PersonAuthService personAuthService) {
        this.personService = personService;
        this.personAuthService = personAuthService;
    }


    @PostMapping("updateInfo")
    public BaseResponse updateUserInfo(@Valid @RequestBody PersonUpdateModel model) {
        personService.update(model, personAuthService.getCurrentPersonId());
        return new BaseResponse(true);
    }

    @PostMapping("createPublicMessage")
    public BaseResponse createPublicMessage(@Valid @RequestBody CreatePublicMessageModel model) {
        personService.createPublicMessage(model, personAuthService.getCurrentPersonId());
        return new BaseResponse(true);
    }

    @GetMapping("getPublicMessages")
    public PublicMessagesResponse getPublicMessages() {
        return personService.getPublicMessages(personAuthService.getCurrentPersonId());
    }

    @PostMapping("sendPublicMessage")
    public BaseResponse sendPublicMessage(@Valid @RequestBody CreatePublicMessageModel model) {
        personService.createPublicMessage(model, personAuthService.getCurrentPersonId());
        return new BaseResponse(true);
    }

    @GetMapping("getPublicMessages/{personId}")
    public PublicMessagesResponse createPublicMessage(@PathVariable int personId) {
        return personService.getPublicMessages(personId);
    }

    @GetMapping("getPersonInfo")
    public PersonFullInfoResponse getPersonInfo() {
        return personService.getPersonFullInfo(personAuthService.getCurrentPersonId());
    }

    @GetMapping("getPersonInfo/{personId}")
    public PersonInfoResponse getPersonInfo(@PathVariable int personId) {
        return personService.getPersonInfo(personId);
    }

    @PostMapping("findPerson")
    public Collection<PersonInfoResponse> findPerson(@RequestBody FindPersonRequest model) {
        return personService.findPerson(model);
    }

    @GetMapping("addFriend/{personId}")
    public BaseResponse addFriend(@PathVariable("personId") int personId) {
        personService.addFriend(personAuthService.getCurrentPersonId(), personId);
        return new BaseResponse(true);
    }

    @GetMapping("deleteFriend/{personId}")
    public BaseResponse deleteFriend(@PathVariable("personId") int personId) {
        personService.deleteFriend(personAuthService.getCurrentPersonId(), personId);
        return new BaseResponse(true);
    }

    @GetMapping("friends")
    public BaseResponseWithResult<Collection<FriendItem>> friends() {
        return new BaseResponseWithResult<>(personService.getFriends(personAuthService.getCurrentPersonId()));
    }

    @GetMapping("friends/{personId}")
    public BaseResponseWithResult<Collection<FriendItem>> friends(@PathVariable("personId") int personId) {
        return new BaseResponseWithResult<>(personService.getFriends(personId));
    }
}
