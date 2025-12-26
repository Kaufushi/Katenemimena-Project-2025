package com.gr.hua.dit.project2025.StreetFoodGo.core.service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateMenuItemDto (
    @NotBlank String name,
    @Positive double price,
    String description,
    boolean available,
    String imageUrl

) {}
