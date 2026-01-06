package com.gr.hua.dit.project2025.StreetFoodGo.web;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Restaurant;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.MenuItemRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.RestaurantRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.security.PersonDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class RestaurantsController {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public RestaurantsController(RestaurantRepository restaurantRepository,
                                 MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }


    // =========================
    // LIST RESTAURANTS
    // =========================
    @GetMapping("/restaurants")
    public String showRestaurants(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String cuisine,
            Model model
    ) {

        String searchTerm = (search == null) ? "" : search.trim();
        String selectedArea = (area == null) ? "" : area.trim();
        String selectedCuisine = (cuisine == null) ? "" : cuisine.trim();

        boolean hasSearch = !searchTerm.isBlank();
        boolean hasArea = !selectedArea.isBlank();
        boolean hasCuisine = !selectedCuisine.isBlank();

        List<Restaurant> restaurants;

        if (hasSearch && hasArea && hasCuisine) {
            restaurants = restaurantRepository
                    .findByNameContainingIgnoreCaseAndAreaIgnoreCaseAndCuisineIgnoreCase(
                            searchTerm, selectedArea, selectedCuisine
                    );
        } else if (hasSearch && hasArea) {
            restaurants = restaurantRepository
                    .findByNameContainingIgnoreCaseAndAreaIgnoreCase(searchTerm, selectedArea);
        } else if (hasSearch && hasCuisine) {
            restaurants = restaurantRepository
                    .findByNameContainingIgnoreCaseAndCuisineIgnoreCase(searchTerm, selectedCuisine);
        } else if (hasArea && hasCuisine) {
            restaurants = restaurantRepository
                    .findByAreaIgnoreCaseAndCuisineIgnoreCase(selectedArea, selectedCuisine);
        } else if (hasSearch) {
            restaurants = restaurantRepository
                    .findByNameContainingIgnoreCase(searchTerm);
        } else if (hasArea) {
            restaurants = restaurantRepository
                    .findByAreaIgnoreCase(selectedArea);
        } else if (hasCuisine) {
            restaurants = restaurantRepository
                    .findByCuisineIgnoreCase(selectedCuisine);
        } else {
            restaurants = restaurantRepository.findAll();
        }

        List<String> areas = restaurantRepository.findAll()
                .stream().map(Restaurant::getArea).distinct().toList();

        List<String> cuisines = restaurantRepository.findAll()
                .stream().map(Restaurant::getCuisine).distinct().toList();

        model.addAttribute("restaurants", restaurants);
        model.addAttribute("areas", areas);
        model.addAttribute("cuisines", cuisines);

        model.addAttribute("search", searchTerm);
        model.addAttribute("selectedArea", selectedArea);
        model.addAttribute("selectedCuisine", selectedCuisine);

        return "restaurants";
    }

    // =========================
    // SINGLE RESTAURANT PAGE
    // =========================
    @GetMapping("/restaurants/{id}")
    public String viewRestaurant(
            @PathVariable Long id,
            Model model,
            @AuthenticationPrincipal PersonDetails userDetails
    ) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        boolean isOwner = false;

        if (userDetails != null) {
            Person loggedInPerson = userDetails.getPerson();
            isOwner = restaurant.getOwner().getId()
                    .equals(loggedInPerson.getId());
        }

        model.addAttribute(
                "menuItems",
                menuItemRepository.findByRestaurantAndAvailableTrue(restaurant)
        );

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("isOwner", isOwner);

        return "restaurant";
    }

    @GetMapping("/restaurants/{id}/checkout")
    public String viewCheckout(@PathVariable Long id, Model model) {
        model.addAttribute("restaurantId", id);
        return "checkout";
    }


}