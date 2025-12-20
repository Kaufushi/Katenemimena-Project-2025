package com.gr.hua.dit.project2025.StreetFoodGo.web;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.PersonType;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.PersonRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Controller
public class RegisterController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    public RegisterController(PersonRepository personRepository,
                              BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam PersonType type
    ) {
        Person person = new Person();
        person.setUsername(username);
        person.setEmailAddress(email);

        person.setType(type);

        person.setPasswordHash(passwordEncoder.encode(password));
        person.setCreatedAt(Instant.now());

        personRepository.save(person);

        return "redirect:/login";
    }
}

