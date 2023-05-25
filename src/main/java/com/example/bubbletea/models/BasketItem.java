package com.example.bubbletea.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Basketitem")
@Getter
@Setter
public class BasketItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    public BasketItem() {
        this.quantity = 1;
    }

    @Override
    public String toString() {
        return "BasketItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", basket=" + basket +
                ", item=" + item +
                '}';
    }
}
