package com.gr.hua.dit.project2025.StreetFoodGo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;


//@RestController
//public class Controller {
//
//}

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // points to home.html in templates folder
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // points to login.html
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // points to register.html
    }

    @GetMapping("/restaurants")
    public String restaurants() {
        return "restaurants"; // points to restaurants.html
    }
}
