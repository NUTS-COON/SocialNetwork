package ru.nutscoon.sn.core.service.impl;

import ru.nutscoon.sn.core.exception.BadCredentialsException;
import ru.nutscoon.sn.core.exception.InvalidOperationException;
import ru.nutscoon.sn.core.exception.NotFoundException;
import ru.nutscoon.sn.core.model.Email;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.model.entity.PersonToken;
import ru.nutscoon.sn.core.model.request.PersonLoginModel;
import ru.nutscoon.sn.core.model.request.PersonRegisterModel;
import ru.nutscoon.sn.core.model.request.ResetPasswordModel;
import ru.nutscoon.sn.core.model.response.ChangePasswordModel;
import ru.nutscoon.sn.core.repository.PersonRepository;
import ru.nutscoon.sn.core.repository.PersonTokenRepository;
import ru.nutscoon.sn.core.service.AuthService;
import ru.nutscoon.sn.core.service.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final PersonRepository personRepository;
    private final PersonTokenRepository personTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;


    public AuthServiceImpl(
            PersonRepository personRepository,
            PersonTokenRepository personTokenRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.personRepository = personRepository;
        this.personTokenRepository = personTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }


    @Override
    public String register(PersonRegisterModel model) {
        if (personRepository.existsByEmail(model.getEmail())) {
            throw new InvalidOperationException("User with same email already exist");
        }

        Person person = new Person();
        person.setEmail(model.getEmail());
        person.setPassword(bCryptPasswordEncoder.encode(model.getPassword()));
        person.setName(model.getName());
        person.setSurname(model.getSurname());
        person = personRepository.save(person);

        PersonToken personToken = createToken(person);
        return personToken.getToken();
    }

    @Override
    public String loginUser(PersonLoginModel model) {
        Person person = personRepository.getByEmail(model.getEmail());
        if (person == null) {
            throw new BadCredentialsException("Wrong email or password");
        }

        if (!bCryptPasswordEncoder.matches(model.getPassword(), person.getPassword())) {
            throw new BadCredentialsException("Wrong email or password");
        }

        PersonToken personToken = createToken(person);
        return personToken.getToken();
    }

    @Override
    @Transactional
    public void logout(String token) {
        personTokenRepository.deleteByToken(token);
    }

    @Override
    @Transactional
    public void logoutAnywhere(int personId) {
        personTokenRepository.deleteAllByPersonId(personId);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordModel model) {
        Person person = personRepository.getByEmail(model.getEmail());
        if(person == null){
            return;
        }

        String newPassword = generatePassword();
        person.setPassword(bCryptPasswordEncoder.encode(newPassword));

        Email emailObj = new Email();
        emailObj.setTo(model.getEmail());
        emailObj.setSubject("Password reset");
        emailObj.setBody("New password: " + newPassword);
        emailService.sendEmail(emailObj);
        logoutAnywhere(person.getId());
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordModel model, int currentPersonId) {
        Person person = personRepository.findById(currentPersonId).orElseThrow(() -> new NotFoundException("Person not found"));

        if (!bCryptPasswordEncoder.matches(model.getCurrentPassword(), person.getPassword())) {
            throw new BadCredentialsException("Wrong email or password");
        }

        if (!model.getEmail().equals(person.getEmail())) {
            throw new BadCredentialsException("Wrong email or password");
        }

        person.setPassword(bCryptPasswordEncoder.encode(model.getNewPassword()));
        logoutAnywhere(currentPersonId);
    }

    private PersonToken createToken(Person person) {
        PersonToken personToken = new PersonToken();
        personToken.setPersonId(person.getId());
        personToken.setToken(UUID.randomUUID().toString());
        personToken = personTokenRepository.save(personToken);

        return personToken;
    }

    private String generatePassword() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
