package com.gr.hua.dit.project2025.StreetFoodGo.web;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//@RestController
//public class Controller {
//
//}

@RequestMapping
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // points to home.html in templates folder
    }



    @GetMapping("/restaurants")
    public String restaurants() {
        return "restaurants"; // points to restaurants.html
    }

    @GetMapping("/menu")
    public String menu()
        { return "menu"; } // points to menu.html

    @GetMapping("/order-status")
    public String orderStatus()
        { return "order_status"; } // points to order_status.html

    @GetMapping("/owner-menu")
    public String ownerMenu()
        { return "owner_menu"; }

    @GetMapping("/owner-orders")
    public String ownerOrders()
        { return "owner_orders"; }

    @GetMapping("/owner-dashboard")
    public String ownerDashboard()
        { return "owner_dashboard"; }
}
