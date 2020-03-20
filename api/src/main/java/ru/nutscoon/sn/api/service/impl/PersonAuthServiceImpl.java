package ru.nutscoon.sn.api.service.impl;

import ru.nutscoon.sn.core.exception.NotAuthenticatedException;
import ru.nutscoon.sn.core.exception.WrongPersonTokenException;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.model.entity.PersonToken;
import ru.nutscoon.sn.core.repository.PersonRepository;
import ru.nutscoon.sn.core.repository.PersonTokenRepository;
import ru.nutscoon.sn.api.service.PersonAuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PersonAuthServiceImpl implements PersonAuthService {

    protected final PersonRepository personRepository;
    protected final PersonTokenRepository personTokenRepository;


    public PersonAuthServiceImpl(PersonRepository personRepository, PersonTokenRepository personTokenRepository) {
        this.personRepository = personRepository;
        this.personTokenRepository = personTokenRepository;
    }


    @Override
    public int getCurrentPersonId() {
        String token = getCurrentUserToken();
        PersonToken personToken = personTokenRepository.getByToken(token);
        if (personToken == null) {
            throw new NotAuthenticatedException();
        }

        Person person = personRepository.findById(personToken.getPersonId()).orElseThrow(WrongPersonTokenException::new);
        return person.getId();
    }

    @Override
    public String getCurrentUserToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new NotAuthenticatedException();
        }

        String token = (String) authentication.getPrincipal();
        if (token == null || token.isEmpty()) {
            throw new NotAuthenticatedException();
        }

        return token;
    }
}
