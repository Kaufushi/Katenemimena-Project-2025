package com.gr.hua.dit.project2025.StreetFoodGo.core.service;

import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.AuthResponse;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.CreateCustomerRequest;

public interface AuthService {

    AuthResponse registerCustomer(CreateCustomerRequest request);



    AuthResponse login(String username, String password);

}
