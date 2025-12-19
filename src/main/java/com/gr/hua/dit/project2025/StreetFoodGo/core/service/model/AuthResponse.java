package com.gr.hua.dit.project2025.StreetFoodGo.core.service.model;

public record AuthResponse (
        String token,
        String username,
        String role
) {}
