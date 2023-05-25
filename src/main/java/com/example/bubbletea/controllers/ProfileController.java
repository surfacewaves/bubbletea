package com.example.bubbletea.controllers;

import com.example.bubbletea.models.Basket;
import com.example.bubbletea.models.Person;
import com.example.bubbletea.services.BasketService;
import com.example.bubbletea.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final PersonService personService;
    private final BasketService basketService;

    @Autowired
    public ProfileController(PersonService personService, BasketService basketService) {
        this.personService = personService;
        this.basketService = basketService;
    }

    @GetMapping()
    public String profile(Model model) {
        Person currentPerson = personService.currentPerson();
        model.addAttribute("person", currentPerson);
        return "profile";
    }

    @GetMapping("/history")
    public String history(Model model) {
        Person currentPerson = personService.currentPerson();
        model.addAttribute("person", currentPerson);
        model.addAttribute("baskets", currentPerson.getBaskets());
        return "history";
    }

    @GetMapping("/history/{id}")
    public String order(@PathVariable("id") int id, Model model) {
        Person currentPerson = personService.currentPerson();
        model.addAttribute("person", currentPerson);
        model.addAttribute("basket", basketService.findById(id).get());
        model.addAttribute("items", basketService.findById(id).get().getBasketItems());
        return "order";
    }
}
