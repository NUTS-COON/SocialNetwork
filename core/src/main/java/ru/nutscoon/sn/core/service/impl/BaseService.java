package ru.nutscoon.sn.core.service.impl;

import ru.nutscoon.sn.core.exception.NotFoundException;
import ru.nutscoon.sn.core.model.entity.Person;
import ru.nutscoon.sn.core.repository.PersonRepository;

public class BaseService {

    protected final PersonRepository personRepository;


    public BaseService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    protected Person getPerson(int personId){
        return personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found"));
    }
}
