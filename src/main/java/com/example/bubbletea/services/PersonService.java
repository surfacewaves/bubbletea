package com.example.bubbletea.services;

import com.example.bubbletea.models.Basket;
import com.example.bubbletea.models.Person;
import com.example.bubbletea.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public Person currentPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //если пользователь - не гость
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            Person person = findByUsername(currentUserName).get();
            return person;
        }

        //а если гость
        return null;
    }

    @Transactional
    public void addBasket(Person person, Basket basket) {
        person.getBaskets().add(basket);
        personRepository.save(person);
    }

}
