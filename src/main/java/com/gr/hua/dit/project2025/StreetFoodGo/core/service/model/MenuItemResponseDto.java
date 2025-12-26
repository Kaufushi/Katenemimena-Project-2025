package com.gr.hua.dit.project2025.StreetFoodGo.core.service.model;

public record MenuItemResponseDto(
        Long id,
        String name,
        String description,
        double price,
        boolean available,
        String imageUrl
) {}
