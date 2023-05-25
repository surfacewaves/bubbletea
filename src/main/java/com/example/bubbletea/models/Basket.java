package com.example.bubbletea.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Basket")
@Getter
@Setter
public class Basket {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "total_price")
    private int total_price;

    @Column(name = "is_completed")
    private boolean is_completed;

    @OneToMany(mappedBy = "basket")
    private List<BasketItem> basketItems;

    public Basket() {
        this.total_price = 0;
        this.is_completed = false;
        this.basketItems = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", basket_items=" + basketItems +
                ", owner=" + owner +
                ", total_price=" + total_price +
                ", is_completed=" + is_completed +
                '}';
    }
}
