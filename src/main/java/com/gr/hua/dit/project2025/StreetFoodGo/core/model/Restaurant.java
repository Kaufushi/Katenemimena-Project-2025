package com.gr.hua.dit.project2025.StreetFoodGo.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String area;
    private String cuisine;
    private String imageUrl;
    private Long telephone;
    private String email;

    public Restaurant() {}

    public Restaurant(String name, String area, String cuisine, String imageUrl, Long telephone, String email) {
        this.name = name;
        this.area = area;
        this.cuisine = cuisine;
        this.imageUrl = imageUrl;
        this.telephone = telephone;
        this.email = email;
    }



}
