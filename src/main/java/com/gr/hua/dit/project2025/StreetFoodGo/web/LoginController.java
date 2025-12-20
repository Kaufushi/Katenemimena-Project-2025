package com.gr.hua.dit.project2025.StreetFoodGo.web;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";   // loads login.html
    }

}
