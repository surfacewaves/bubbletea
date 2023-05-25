package com.example.bubbletea.services;

import com.example.bubbletea.models.Basket;
import com.example.bubbletea.models.BasketItem;
import com.example.bubbletea.models.Item;
import com.example.bubbletea.repositories.BasketItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class Basket_ItemService {
    private final BasketItemRepository basketItemRepository;
    private final ItemService itemService;
    private final BasketService basketService;

    @Autowired
    public Basket_ItemService(BasketItemRepository basketItemRepository, ItemService itemService, BasketService basketService) {
        this.basketItemRepository = basketItemRepository;
        this.itemService = itemService;
        this.basketService = basketService;
    }

    public Optional<BasketItem> findById(int id) {
        return basketItemRepository.findById(id);
    }

    @Transactional
    public void save(BasketItem basketItem) {
        basketItemRepository.save(basketItem);
    }

    @Transactional
    public void delete(int id) {
        basketItemRepository.deleteById(id);
    }

    @Transactional
    public void addBasketItem(Basket basket, Item item) {

        //если товар с таким id уже есть, то прибавляем количество
        for (BasketItem basketItem : basket.getBasketItems()) {
            if (basketItem.getItem().getId() == item.getId()) {
                basketItem.setQuantity(basketItem.getQuantity() + 1);
                basket.setTotal_price(basket.getTotal_price() + basketItem.getItem().getPrice());

                save(basketItem);
                basketService.save(basket);
                return;
            }
        }

        //иначе создаем новый
        BasketItem basketItem = new BasketItem();

        basketItem.setItem(item);
        basketItem.setBasket(basket);
        save(basketItem);

        itemService.addBasketItem(item, basketItem);
        basketService.addBasketItemNotFirstly(basket, basketItem);
    }
}
