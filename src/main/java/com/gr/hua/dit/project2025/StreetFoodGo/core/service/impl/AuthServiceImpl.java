package com.gr.hua.dit.project2025.StreetFoodGo.core.service.impl;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.PersonType;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.PersonRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.AuthService;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.AuthResponse;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.CreateCustomerRequest;
import com.gr.hua.dit.project2025.StreetFoodGo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 Implementation of the AuthService interface,
 Handles customer registration and login for the app
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    // repo for accessing person entities in the database
    private final PersonRepository personRepository;
    // PasswordEncoder to securely hash passwords
    private final PasswordEncoder passwordEncoder;
    // JWT service to issue tokens for authenticated users
    private final JwtService jwtService;

    @Override
    public AuthResponse registerCustomer(CreateCustomerRequest request) {
        if (personRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (personRepository.existsByEmailAddress(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Create new person
        Person person = new Person();
        person.setUsername(request.username());
        person.setFirstname(request.firstName());
        person.setLastname(request.lastName());
        person.setEmailAddress(request.email());
        person.setPasswordHash(passwordEncoder.encode(request.password()));
        person.setType(PersonType.CUSTOMER);

        // Save to database
        personRepository.save(person);

        // Generate JWT token
        String token = jwtService.issue(person.getUsername(),
                java.util.List.of(person.getType().toString()));

        return new AuthResponse(token, person.getUsername(), person.getType().toString());
    }

    @Override
    public AuthResponse login(String username, String password) {

        // Find user by username or email
        Person person = personRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check password
        if (!passwordEncoder.matches(password, person.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtService.issue(person.getUsername(),
                java.util.List.of(person.getType().toString()));

        return new AuthResponse(token, person.getUsername(), person.getType().toString());
    }
}
