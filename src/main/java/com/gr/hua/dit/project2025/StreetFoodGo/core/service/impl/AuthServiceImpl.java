package com.gr.hua.dit.project2025.StreetFoodGo.core.service.impl;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.PersonType;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.PersonRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.AuthService;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.AuthResponseDto;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.CreateCustomerRequestDto;
import com.gr.hua.dit.project2025.StreetFoodGo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the AuthService interface.
 * Handles customer registration and login.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponseDto registerCustomer(CreateCustomerRequestDto request) {

        if (personRepository.existsByEmailAddress(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Person person = new Person();
        person.setUsername(request.username());
        person.setFirstname(request.firstName());
        person.setLastname(request.lastName());
        person.setEmailAddress(request.email());
        person.setPasswordHash(passwordEncoder.encode(request.password()));



        personRepository.save(person);

        String token = jwtService.issue(
                person.getUsername(),
                List.of("ROLE_" + person.getType().name())
        );

        return new AuthResponseDto(
                token,
                person.getUsername(),
                person.getType().name()
        );
    }

    @Override
    public AuthResponseDto login(String email, String password) {

        Person person = personRepository.findByEmailAddress(email);

        if (person == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!passwordEncoder.matches(password, person.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtService.issue(
                person.getUsername(),
                List.of("ROLE_" + person.getType().name())
        );

        return new AuthResponseDto(
                token,
                person.getUsername(),
                person.getType().name()
        );
    }
}
