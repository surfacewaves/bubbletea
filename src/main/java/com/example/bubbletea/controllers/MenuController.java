package com.example.bubbletea.controllers;

import com.example.bubbletea.models.Person;
import com.example.bubbletea.services.Basket_ItemService;
import com.example.bubbletea.services.ItemService;
import com.example.bubbletea.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("menu")
public class MenuController {
    private final ItemService itemService;
    private final Basket_ItemService basket_itemService;
    private final PersonService personService;

    @Autowired
    public MenuController(ItemService itemService, Basket_ItemService basketItemService, PersonService personService) {
        this.itemService = itemService;
        basket_itemService = basketItemService;
        this.personService = personService;
    }

    @GetMapping()
    public String menu(Model model) {
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
        }

        model.addAttribute("Basket_ItemService", basket_itemService);
        model.addAttribute("items", itemService.findAll());
        return "menu";
    }

    @PostMapping("plus")
    public String menu() {
        return "redirect:/menu";
    }
}

