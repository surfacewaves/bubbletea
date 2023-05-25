package com.example.bubbletea.repositories;

import com.example.bubbletea.models.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Integer> {
}

