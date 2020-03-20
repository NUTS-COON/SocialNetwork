package ru.nutscoon.sn.api.controller;

import ru.nutscoon.sn.core.model.request.*;
import ru.nutscoon.sn.core.model.response.*;
import ru.nutscoon.sn.api.service.PersonAuthService;
import ru.nutscoon.sn.core.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("api/public")
public class PublicController {

    private final PublicService publicService;
    private final PersonAuthService personAuthService;


    @Autowired
    public PublicController(PublicService publicService, PersonAuthService personAuthService) {
        this.publicService = publicService;
        this.personAuthService = personAuthService;
    }


    @PostMapping("createPublic")
    public BaseResponseWithResult<Integer> createPublic(@Valid @RequestBody CreatePublicModel model) {
        int publicId = publicService.create(model, personAuthService.getCurrentPersonId());
        return new BaseResponseWithResult<>(publicId);
    }

    @PostMapping("updatePublicInfo")
    public BaseResponse updatePublicInfo(@Valid @RequestBody UpdatePublicModel model) {
        publicService.update(model, personAuthService.getCurrentPersonId());
        return new BaseResponse(true);
    }

    @GetMapping("info/{publicId}")
    public BaseResponseWithResult<PublicInfoResponse> getPublicInfo(@PathVariable("publicId") int publicId) {
        return new BaseResponseWithResult<>(publicService.getPublicInfo(publicId));
    }

    @GetMapping("subscribers/{publicId}")
    public BaseResponseWithResult<Collection<PublicSubscriber>> getPublicSubscribers(@PathVariable("publicId") int publicId) {
        return new BaseResponseWithResult<>(publicService.getSubscribers(publicId));
    }

    @PostMapping("findPublics")
    public BaseResponseWithResult<FindPublicResponse> findPublics(@Valid @RequestBody FindPublicModel model) {
        return new BaseResponseWithResult<>(publicService.find(model));
    }

    @GetMapping("getPublics")
    public BaseResponseWithResult<FindPublicResponse> getPublics(@RequestParam PublicRelationType relationType) {
        return new BaseResponseWithResult<>(publicService.getPublics(relationType, personAuthService.getCurrentPersonId()));
    }

    @GetMapping("subscribe/{publicId}")
    public BaseResponse subscribe(@PathVariable("publicId") int publicId) {
        publicService.subscribe(personAuthService.getCurrentPersonId(), publicId);
        return new BaseResponse(true);
    }

    @GetMapping("unsubscribe/{publicId}")
    public BaseResponse unsubscribe(@PathVariable("publicId") int publicId) {
        publicService.unsubscribe(personAuthService.getCurrentPersonId(), publicId);
        return new BaseResponse(true);
    }

    @PostMapping("assignAdminRights")
    public BaseResponse assignAdminRights(@Valid @RequestBody MakePublicAdminModel model){
        publicService.assignAdminRights(model, personAuthService.getCurrentPersonId());
        return new BaseResponse(true);
    }
}
