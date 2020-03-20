package ru.nutscoon.sn.core.service;

import ru.nutscoon.sn.core.model.request.*;
import ru.nutscoon.sn.core.model.response.FindPublicResponse;
import ru.nutscoon.sn.core.model.response.PublicInfoResponse;
import ru.nutscoon.sn.core.model.response.PublicSubscriber;

import java.util.Collection;

public interface PublicService {
    int create(CreatePublicModel model, int personId);
    void update(UpdatePublicModel model, int personId);
    FindPublicResponse find(FindPublicModel model);
    FindPublicResponse getPublics(PublicRelationType relationType, int personId);
    void subscribe(int personId, int publicId);
    void unsubscribe(int personId, int publicId);
    void assignAdminRights(MakePublicAdminModel model, int ownerId);
    PublicInfoResponse getPublicInfo(int publicId);
    Collection<PublicSubscriber> getSubscribers(int publicId);
}
