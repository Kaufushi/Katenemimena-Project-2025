package com.gr.hua.dit.project2025.StreetFoodGo.web;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Order;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.OrderRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.security.PersonDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerOrderController {

    private final OrderRepository orderRepository;

    public CustomerOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // =========================
    // VIEW CUSTOMER ORDERS
    // =========================
    @GetMapping("/orders")
    public String viewMyOrders(
            @AuthenticationPrincipal PersonDetails userDetails,
            Model model
    ) {
        // Get all orders for this customer
        List<Order> orders = orderRepository.findByCustomerOrderByCreatedAtDesc(
                userDetails.getPerson()
        );

        model.addAttribute("orders", orders);

        return "customerOrders";
    }
}