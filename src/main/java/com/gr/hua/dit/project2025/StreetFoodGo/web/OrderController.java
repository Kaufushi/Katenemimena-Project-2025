package com.gr.hua.dit.project2025.StreetFoodGo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.*;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.MenuItemRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.OrderRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.PersonRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.RestaurantRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.CartItemDTO;
import com.gr.hua.dit.project2025.StreetFoodGo.security.PersonDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final PersonRepository personRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/place")
    public String placeOrder(
            @RequestParam Long restaurantId,
            @RequestParam String cartData,
            @AuthenticationPrincipal PersonDetails userDetails,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        Person customer = personRepository
                .findById(userDetails.getPerson().getId())
                .orElseThrow();

        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow();

        List<CartItemDTO> cartItems =
                objectMapper.readValue(cartData,
                        new TypeReference<>() {
                        });

        Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .status(Order.Status.PENDING)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItemDTO cartItem : cartItems) {

            MenuItem menuItem = menuItemRepository
                    .findByNameAndRestaurant(cartItem.getName(), restaurant)
                    .orElseThrow();

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .menuItem(menuItem)
                    .quantity(cartItem.getQuantity())
                    .price(menuItem.getPrice())
                    .build();

            total += menuItem.getPrice() * cartItem.getQuantity();
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        orderRepository.save(order);

        // Add success message
        redirectAttributes.addFlashAttribute("success", "Order placed successfully! Order #" + order.getId());

        // Redirect back to the restaurant page
        return "redirect:/restaurants/" + restaurantId;
    }
}