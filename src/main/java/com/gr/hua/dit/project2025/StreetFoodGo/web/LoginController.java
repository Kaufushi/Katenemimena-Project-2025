package com.gr.hua.dit.project2025.StreetFoodGo.web;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class LoginController {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(PersonRepository personRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";   // loads login.html
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        // Try to find user by email or firstname
        Person user = personRepository
                .findAll()
                .stream()
                .filter(p -> p.getEmailAddress().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);


        if (user == null) {
            model.addAttribute("error", "User not found");
            return "login";
        }

        // Check hashed password
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            model.addAttribute("error", "Incorrect password");
            return "login";
        }

        // Login success → redirect to homepage
        return "redirect:/";
    }
}
