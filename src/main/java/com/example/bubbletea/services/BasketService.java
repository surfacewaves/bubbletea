package com.example.bubbletea.services;

import com.example.bubbletea.models.Basket;
import com.example.bubbletea.models.BasketItem;
import com.example.bubbletea.models.Person;
import com.example.bubbletea.repositories.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BasketService {
    private final BasketRepository basketRepository;
    private final PersonService personService;

    @Autowired
    public BasketService(BasketRepository basketRepository, PersonService personService) {
        this.basketRepository = basketRepository;
        this.personService = personService;
    }

    public Optional<Basket> findById(int id) {
        return basketRepository.findById(id);
    }

    @Transactional
    public void save(Basket basket) {
        basketRepository.save(basket);
    }

    @Transactional
    public void newBasket(Person person) {
        Basket basket = new Basket();
        basket.setOwner(person);
        save(basket);
        personService.addBasket(person, basket);
    }

    @Transactional
    public void addBasketItem(Basket basket, BasketItem basket_item) {
        basket.getBasketItems().add(basket_item);
        basket.setTotal_price(basket_item.getItem().getPrice());
        save(basket);
    }

    @Transactional
    public void addBasketItemNotFirstly(Basket basket, BasketItem basket_item) {
        basket.getBasketItems().add(basket_item);
        basket.setTotal_price(basket.getTotal_price() + basket_item.getItem().getPrice());
        save(basket);
    }

    public Basket isCurrentBasket(List<Basket> baskets) {
        for (Basket basket : baskets) {
            if (!basket.is_completed())
                return basket;
        }
        return null;
    }
}
