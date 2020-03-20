package ru.nutscoon.sn.core.service;

import ru.nutscoon.sn.core.model.request.PersonLoginModel;
import ru.nutscoon.sn.core.model.request.PersonRegisterModel;
import ru.nutscoon.sn.core.model.request.ResetPasswordModel;
import ru.nutscoon.sn.core.model.response.ChangePasswordModel;

public interface AuthService {
    String register(PersonRegisterModel model);
    String loginUser(PersonLoginModel model);
    void logout(String token);
    void logoutAnywhere(int personId);
    void resetPassword(ResetPasswordModel model);
    void changePassword(ChangePasswordModel model, int currentPersonId);
}
