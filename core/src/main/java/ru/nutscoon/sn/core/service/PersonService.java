package ru.nutscoon.sn.core.service;

import ru.nutscoon.sn.core.model.request.FindPersonRequest;
import ru.nutscoon.sn.core.model.request.PersonUpdateModel;
import ru.nutscoon.sn.core.model.request.CreatePublicMessageModel;
import ru.nutscoon.sn.core.model.response.FriendItem;
import ru.nutscoon.sn.core.model.response.PersonFullInfoResponse;
import ru.nutscoon.sn.core.model.response.PersonInfoResponse;
import ru.nutscoon.sn.core.model.response.PublicMessagesResponse;

import java.util.Collection;

public interface PersonService {
    void update(PersonUpdateModel model, int personId);
    void createPublicMessage(CreatePublicMessageModel model, int personId);
    PublicMessagesResponse getPublicMessages(int personId);
    PersonFullInfoResponse getPersonFullInfo(int personId);
    PersonInfoResponse getPersonInfo(int personId);
    Collection<PersonInfoResponse> findPerson(FindPersonRequest model);
    void addFriend(int currentPersonId, int personId);
    void deleteFriend(int currentPersonId, int personId);
    Collection<FriendItem> getFriends(int personId);
}
