package com.gr.hua.dit.project2025.StreetFoodGo.core.service.model;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank String email,
        @NotBlank String password
) {}