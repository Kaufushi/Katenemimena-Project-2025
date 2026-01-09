package com.gr.hua.dit.project2025.StreetFoodGo.web;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




@RequestMapping
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/owner-dashboard")
    public String ownerDashboard()
        { return "customerOrders"; }
}
