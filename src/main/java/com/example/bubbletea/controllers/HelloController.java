package com.example.bubbletea.controllers;

import com.example.bubbletea.models.Person;
import com.example.bubbletea.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("hello")
public class HelloController {
    private final PersonService personService;

    @Autowired
    public HelloController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String hello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //если пользователь - гость
        if (authentication instanceof AnonymousAuthenticationToken)
            model.addAttribute("person", null);

        //если пользователь - юзер или админ
        else {
            String currentUserName = authentication.getName();
            Optional<Person> person = personService.findByUsername(currentUserName);
            Person current = person.get();
            model.addAttribute("person", current);
            System.out.println(person.get());
        }
        return "index";
    }
}
