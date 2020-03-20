package ru.nutscoon.sn;

import ru.nutscoon.sn.SnApplication;
import ru.nutscoon.sn.core.exception.InvalidOperationException;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.model.entity.PersonToken;
import ru.nutscoon.sn.core.model.request.PersonLoginModel;
import ru.nutscoon.sn.core.model.request.PersonRegisterModel;
import ru.nutscoon.sn.core.repository.PersonRepository;
import ru.nutscoon.sn.core.repository.PersonTokenRepository;
import ru.nutscoon.sn.core.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = SnApplication.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class AuthServiceTest {

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private PersonTokenRepository personTokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthService authService;

    @Test
    public void CanNotUseSameEmailForRegister() {
        String email = "email";
        PersonRegisterModel model = new PersonRegisterModel();
        model.setName(email);

        Mockito.when(personRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(InvalidOperationException.class, () -> authService.register(model));
    }

    @Test
    public void afterRegisterCanLogin() {
        Person person = new Person();
        person.setEmail("Email");
        person.setPassword(bCryptPasswordEncoder.encode("Password"));

        PersonToken token = new PersonToken();
        token.setToken("Token");
        token.setPersonId(person.getId());

        Mockito.doReturn(false)
                .when(personRepository)
                .existsByEmail("Email");

        Mockito.when(personRepository.save(any())).thenReturn(person);
        Mockito.when(personTokenRepository.save(any())).thenReturn(token);

        PersonRegisterModel model = new PersonRegisterModel();
        model.setName("Email");
        model.setName("Name");
        model.setSurname("Surname");
        model.setPassword("Password");

        assertDoesNotThrow(() -> authService.register(model));

        PersonLoginModel loginModel = new PersonLoginModel();
        loginModel.setEmail("Email");
        loginModel.setPassword("Password");

        Mockito.doReturn(person)
                .when(personRepository)
                .getByEmail("Email");

        assertDoesNotThrow(() -> authService.loginUser(loginModel));
    }
}
