package com.gr.hua.dit.project2025.StreetFoodGo.core.service.model;

public record AuthResponseDto(
        String token,
        String username,
        String role
) {}
