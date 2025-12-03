package com.gr.hua.dit.project2025.StreetFoodGo.core.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double price;

    private boolean available;

    private String imageUrl;


    // Many menu items belong to one store
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Restaurant restaurant;
}
