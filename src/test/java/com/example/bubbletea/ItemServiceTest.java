package com.example.bubbletea;

import com.example.bubbletea.models.BasketItem;
import com.example.bubbletea.models.Item;
import com.example.bubbletea.repositories.ItemRepository;
import com.example.bubbletea.services.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @Test
    void findAll() {
        Item item1 = new Item();
        item1.setName("Test1");
        item1.setPrice(100);

        Item item2 = new Item();
        item2.setName("Test2");
        item2.setPrice(200);

        Mockito.when(itemRepository.findAll()).thenReturn(List.of(item1, item2));

        ItemService itemService = new ItemService(itemRepository);
        Assertions.assertEquals(2, itemService.findAll().size());
        Assertions.assertEquals("Test1", itemService.findAll().get(0).getName());
    }

    @Test
    void findById() {
        Item item = new Item();
        item.setName("Test3");
        item.setId(100);

        Mockito.when(itemRepository.findById(100)).thenReturn(Optional.of(item));

        ItemService itemService = new ItemService(itemRepository);
        Assertions.assertEquals("Test3", itemService.findById(100).get().getName());
    }

    @Test
    void addBasketItem() {
        Item item = new Item();
        item.setName("Test4");

        BasketItem basketItem = new BasketItem();
        basketItem.setId(100);
        List<BasketItem> basketItems = new ArrayList<>();
        basketItems.add(basketItem);
        item.setBasketItems(basketItems);
        itemRepository.save(item);

        ArgumentCaptor<Item> requestCaptor = ArgumentCaptor.forClass(Item.class);
        Mockito.verify(itemRepository).save(requestCaptor.capture());
        Item captured = requestCaptor.getValue();

        Assertions.assertEquals(100, captured.getBasketItems().get(0).getId());
    }
}
