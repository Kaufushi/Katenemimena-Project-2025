package com.gr.hua.dit.project2025.StreetFoodGo.web;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.MenuItem;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Order;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Restaurant;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.MenuItemRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.OrderRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.RestaurantRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.security.PersonDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerRestaurantController {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public OwnerRestaurantController(RestaurantRepository restaurantRepository,
                                     MenuItemRepository menuItemRepository,
                                     OrderRepository orderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
    }

    // =========================
    // MANAGE RESTAURANT PAGE
    // =========================
    @GetMapping("/restaurants/{id}/manage")
    public String manageRestaurant(@PathVariable Long id, Model model) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("menuItems",
                menuItemRepository.findByRestaurant(restaurant));

        return "manageRestaurant";
    }

    // =========================
    // OPEN / CLOSE RESTAURANT
    // =========================
    @PostMapping("/restaurants/{id}/toggle")
    public String toggleRestaurant(@PathVariable Long id) {

        Restaurant r = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        r.setOpen(!r.getOpen());
        restaurantRepository.save(r);

        return "redirect:/owner/restaurants/" + id + "/manage";
    }


    // =========================
    // ADD MENU ITEM
    // =========================
    @PostMapping("/restaurants/{id}/menu/add")
    public String addMenuItem(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam double price,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) boolean available
    ) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        MenuItem item = MenuItem.builder()
                .name(name)
                .description(description)
                .price(price)
                .imageUrl(imageUrl)
                .available(available)
                .restaurant(restaurant)
                .build();

        menuItemRepository.save(item);

        return "redirect:/owner/restaurants/" + id + "/manage";
    }

    // =========================
    // TOGGLE MENU ITEM
    // =========================
    @PostMapping("/menu/{id}/toggle")
    public String toggleMenuItem(@PathVariable Long id) {

        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

        item.setAvailable(!item.isAvailable());
        menuItemRepository.save(item);

        return "redirect:/owner/restaurants/" +
                item.getRestaurant().getId() + "/manage";
    }

    // =========================
    // DELETE MENU ITEM
    // =========================
    @PostMapping("/menu/{id}/delete")
    public String deleteMenuItem(@PathVariable Long id) {

        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

        Long restaurantId = item.getRestaurant().getId();
        menuItemRepository.delete(item);

        return "redirect:/owner/restaurants/" + restaurantId + "/manage";
    }

    // =========================
    // DELETE RESTAURANT
    // =========================
    @PostMapping("/restaurants/{id}/delete")
    public String deleteRestaurant(@PathVariable Long id) {

        restaurantRepository.deleteById(id);
        return "redirect:/restaurants";
    }

    // =========================
    // VIEW RESTAURANT ORDERS
    // =========================
    @GetMapping("/restaurants/{id}/orders")
    public String viewOrders(
            @PathVariable Long id,
            @AuthenticationPrincipal PersonDetails userDetails,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Check if the logged-in user is the owner
        if (!restaurant.getOwner().getId().equals(userDetails.getPerson().getId())) {
            redirectAttributes.addFlashAttribute("error", "You are not authorized to view these orders");
            return "redirect:/restaurants/" + id;
        }

        // Get all orders for this restaurant
        List<Order> orders = orderRepository.findByRestaurantId(id);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("orders", orders);

        return "ownerOrders";
    }

    // =========================
    // UPDATE ORDER STATUS
    // =========================
    @PostMapping("/restaurants/{restaurantId}/orders/{orderId}/status")
    public String updateOrderStatus(
            @PathVariable Long restaurantId,
            @PathVariable Long orderId,
            @RequestParam Order.Status status,
            @AuthenticationPrincipal PersonDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Check if the logged-in user is the owner
        if (!restaurant.getOwner().getId().equals(userDetails.getPerson().getId())) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized");
            return "redirect:/restaurants/" + restaurantId;
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        orderRepository.save(order);

        redirectAttributes.addFlashAttribute("success", "Order status updated successfully");
        return "redirect:/owner/restaurants/" + restaurantId + "/orders";
    }
}