package com.example.bubbletea.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Person")
@Getter
@Setter
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "username must not be empty")
    @Size(min = 2, max = 50, message = "username must be between 2 and 20 characters")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "lastname must not be empty")
    @Size(min = 2, max = 50, message = "lastname must be between 2 and 50 characters")
    @Column(name = "last_name")
    private String last_name;

    @NotEmpty(message = "firstname must not be empty")
    @Size(min = 2, max = 50, message = "firstname must be between 2 and 50 characters")
    @Column(name = "first_name")
    private String first_name;

    @NotEmpty(message = "address must not be empty")
    @Size(min = 2, max = 50, message = "address must be between 2 and 50 characters")
    @Column(name = "address")
    private String address;

    @NotEmpty(message = "password must not be empty")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "owner")
    private List<Basket> baskets;


    public Person() {
    }

    public Person(String username, String last_name, String first_name, String address) {
        this.username = username;
        this.last_name = last_name;
        this.first_name = first_name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", last_name='" + last_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
