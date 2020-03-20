package ru.nutscoon.sn.api.controller;

import ru.nutscoon.sn.core.model.request.GetPostsModel;
import ru.nutscoon.sn.core.model.request.SendPostModel;
import ru.nutscoon.sn.core.model.response.BaseResponse;
import ru.nutscoon.sn.core.model.response.BaseResponseWithResult;
import ru.nutscoon.sn.core.model.response.GetPostsResponse;
import ru.nutscoon.sn.api.service.PersonAuthService;
import ru.nutscoon.sn.core.service.PublicPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/post")
public class PublicPostController {
    private final PublicPostService publicPostService;
    private final PersonAuthService personAuthService;


    @Autowired
    public PublicPostController(PublicPostService publicPostService, PersonAuthService personAuthService) {
        this.publicPostService = publicPostService;
        this.personAuthService = personAuthService;
    }


    @PostMapping("sendPost")
    public BaseResponseWithResult<Integer> sendPost(@Valid @RequestBody SendPostModel model) {
        int postId = publicPostService.sendPost(model, personAuthService.getCurrentPersonId());
        return new BaseResponseWithResult<>(postId);
    }

    @GetMapping("publishPost/{postId}")
    public BaseResponse publishPost(@PathVariable("postId") int postId) {
        publicPostService.publishPost(personAuthService.getCurrentPersonId(), postId);
        return new BaseResponse(true);
    }

    @PostMapping("getPosts")
    public BaseResponseWithResult<GetPostsResponse> getPosts(@Valid @RequestBody GetPostsModel model) {
        return new BaseResponseWithResult<>(publicPostService.getPosts(model, personAuthService.getCurrentPersonId()));
    }
}
