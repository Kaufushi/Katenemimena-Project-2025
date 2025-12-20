package com.gr.hua.dit.project2025.StreetFoodGo.core.service;

import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.AuthResponseDto;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.CreateCustomerRequestDto;

public interface AuthService {

    AuthResponseDto registerCustomer(CreateCustomerRequestDto request);



    AuthResponseDto login(String username, String password);

}
