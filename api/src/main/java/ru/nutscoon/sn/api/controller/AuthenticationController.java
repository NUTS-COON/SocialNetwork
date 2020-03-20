package ru.nutscoon.sn.api.controller;

import ru.nutscoon.sn.core.model.request.PersonLoginModel;
import ru.nutscoon.sn.core.model.request.PersonRegisterModel;
import ru.nutscoon.sn.core.model.request.ResetPasswordModel;
import ru.nutscoon.sn.core.model.response.BaseResponse;
import ru.nutscoon.sn.core.model.response.BaseResponseWithResult;
import ru.nutscoon.sn.core.model.response.ChangePasswordModel;
import ru.nutscoon.sn.core.service.AuthService;
import ru.nutscoon.sn.api.service.PersonAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthService authService;
    private final PersonAuthService personAuthService;

    @Autowired
    public AuthenticationController(AuthService authService, PersonAuthService personAuthService) {
        this.authService = authService;
        this.personAuthService = personAuthService;
    }

    @PostMapping("register")
    public BaseResponseWithResult<String> register(@Valid @RequestBody PersonRegisterModel model) {
        String token = authService.register(model);
        return new BaseResponseWithResult<>(token);
    }

    @PostMapping("login")
    public BaseResponseWithResult<String> login(@Valid @RequestBody PersonLoginModel model) {
        String token = authService.loginUser(model);
        return new BaseResponseWithResult<>(token);
    }

    @GetMapping("logout")
    public BaseResponse logout() {
        authService.logout(personAuthService.getCurrentUserToken());
        return new BaseResponse(true);
    }

    @GetMapping("logoutAnywhere")
    public BaseResponse logoutAnywhere() {
        authService.logoutAnywhere(personAuthService.getCurrentPersonId());
        return new BaseResponse(true);
    }

    @PostMapping("changePassword")
    public BaseResponse changePassword(@Valid @RequestBody ChangePasswordModel model) {
        authService.changePassword(model, personAuthService.getCurrentPersonId());
        return new BaseResponse(true);
    }

    @PostMapping("resetPassword")
    public BaseResponse resetPassword(@Valid @RequestBody ResetPasswordModel model) {
        authService.resetPassword(model);
        return new BaseResponse(true);
    }
}
