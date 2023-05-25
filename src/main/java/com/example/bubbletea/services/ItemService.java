package com.example.bubbletea.services;

import com.example.bubbletea.models.BasketItem;
import com.example.bubbletea.models.Item;
import com.example.bubbletea.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(int id) {
        return itemRepository.findById(id);
    }

    @Transactional
    public void addBasketItem(Item item, BasketItem basket_item) {
        item.getBasketItems().add(basket_item);
        itemRepository.save(item);
    }
}
