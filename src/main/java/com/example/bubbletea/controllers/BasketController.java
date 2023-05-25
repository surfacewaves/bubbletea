package com.example.bubbletea.controllers;

import com.example.bubbletea.models.Basket;
import com.example.bubbletea.models.BasketItem;
import com.example.bubbletea.models.Item;
import com.example.bubbletea.models.Person;
import com.example.bubbletea.repositories.BasketItemRepository;
import com.example.bubbletea.services.BasketService;
import com.example.bubbletea.services.Basket_ItemService;
import com.example.bubbletea.services.ItemService;
import com.example.bubbletea.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("basket")
@Controller
public class BasketController {
    private final PersonService personService;
    private final BasketService basketService;
    private final Basket_ItemService basket_itemService;
    private final ItemService itemService;

    @Autowired
    public BasketController(PersonService personService, BasketService basketService, Basket_ItemService basketItemService, ItemService itemService) {
        this.personService = personService;
        this.basket_itemService = basketItemService;
        this.basketService = basketService;
        this.itemService = itemService;
    }

    @GetMapping()
    public String basketPage(Model model) {
        Person currentPerson = personService.currentPerson();
        Basket basket = basketService.isCurrentBasket(currentPerson.getBaskets());
        model.addAttribute("person", currentPerson);
        model.addAttribute("basket", basket);
        return "basket";
    }

    @PostMapping("/add/{id}")
    public String addToBasket(@PathVariable(name = "id") int itemId) {
        Person currentPerson = personService.currentPerson();
        Item item = itemService.findById(itemId).get();
        List<Basket> baskets = currentPerson.getBaskets();
        if (currentPerson != null) {
            if (baskets.isEmpty() || basketService.isCurrentBasket(baskets) == null) {
                basketService.newBasket(currentPerson);
                basket_itemService.addBasketItem(baskets.get(baskets.size() - 1), item);
            }
            else if (basketService.isCurrentBasket(baskets) != null) {
                basket_itemService.addBasketItem(basketService.isCurrentBasket(baskets), item);
            }
            return "redirect:/menu";
        }

        return "redirect:/auth/login";
    }

    @PostMapping("/remove1/{id}")
    public String removeOne(@PathVariable(name = "id") int basketItemId) {
        Person currentPerson = personService.currentPerson();
        List<Basket> baskets = currentPerson.getBaskets();
        Basket basket = basketService.isCurrentBasket(baskets);
        BasketItem basketItem = basket_itemService.findById(basketItemId).get();

        if (basketItem.getQuantity() == 1) {
            basket.getBasketItems().remove(basketItem);
            basket.setTotal_price(basket.getTotal_price() - basketItem.getItem().getPrice());

            basket_itemService.delete(basketItemId);
            basketService.save(basket);
        }

        else {
            basketItem.setQuantity(basketItem.getQuantity() - 1);
            basket.setTotal_price(basket.getTotal_price() - basketItem.getItem().getPrice());

            basket_itemService.save(basketItem);
            basketService.save(basket);
        }

        return "redirect:/basket";
    }

    @PostMapping("/add1/{id}")
    public String addOne(@PathVariable(name = "id") int basketItemId) {
        Person currentPerson = personService.currentPerson();
        List<Basket> baskets = currentPerson.getBaskets();
        Basket basket = basketService.isCurrentBasket(baskets);
        BasketItem basketItem = basket_itemService.findById(basketItemId).get();

        basketItem.setQuantity(basketItem.getQuantity() + 1);
        basket.setTotal_price(basket.getTotal_price() + basketItem.getItem().getPrice());

        basket_itemService.save(basketItem);
        basketService.save(basket);

        return "redirect:/basket";
    }

    @PostMapping("/makeOrder/{id}")
    public String makeOrder(@PathVariable(name = "id") int basketId) {
        Basket basket = basketService.findById(basketId).get();
        basket.set_completed(true);
        basketService.save(basket);

        return "redirect:/basket";
    }
}
