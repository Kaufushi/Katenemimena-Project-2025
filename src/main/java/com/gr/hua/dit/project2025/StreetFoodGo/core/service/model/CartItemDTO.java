package com.gr.hua.dit.project2025.StreetFoodGo.core.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private String name;
    private double price;
    private int quantity;
}
