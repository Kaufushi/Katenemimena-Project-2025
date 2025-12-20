package com.gr.hua.dit.project2025.StreetFoodGo.core.service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record CreateCustomerRequestDto(
                @NotNull @NotBlank @Size(max = 50) String username,
                @NotNull @NotBlank @Size(max = 100) String firstName,
                @NotNull @NotBlank @Size(max = 100) String lastName,
                @NotNull @NotBlank @Size(max = 100) String email,
                @NotNull @NotBlank @Size(min = 6, max = 24) String password
) {}