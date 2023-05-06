package com.example.bubbletea.util;

import com.example.bubbletea.models.Person;
import com.example.bubbletea.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person)target;
        if (personService.findByUsername(person.getUsername()).isPresent())
            errors.rejectValue("username", "", "Такой пользователь уже существует");
    }
}
