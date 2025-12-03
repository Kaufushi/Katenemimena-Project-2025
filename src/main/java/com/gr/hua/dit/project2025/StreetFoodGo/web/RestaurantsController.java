package com.gr.hua.dit.project2025.StreetFoodGo.web;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Restaurant;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.RestaurantRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RestaurantsController {

    private final RestaurantRepository restaurantRepository;

    public RestaurantsController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/restaurants")
    public String showRestaurants(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String cuisine,
            Model model
    ) {
        // Default values
        String searchTerm = (search == null) ? "" : search;
        String selectedArea = (area == null || area.isEmpty()) ? "" : area;
        String selectedCuisine = (cuisine == null || cuisine.isEmpty()) ? "" : cuisine;

        // Load restaurants filtered
        List<Restaurant> restaurants = restaurantRepository.findByNameContainingIgnoreCaseAndAreaIgnoreCaseAndCuisineIgnoreCase(
                searchTerm,
                selectedArea,
                selectedCuisine
        );

        // Areas list (distinct)
        List<String> areas = restaurantRepository.findAll()
                .stream()
                .map(Restaurant::getArea)
                .distinct().toList();

        // Cuisines list (distinct)
        List<String> cuisines = restaurantRepository.findAll()
                .stream()
                .map(Restaurant::getCuisine)
                .distinct().toList();

        model.addAttribute("restaurants", restaurants);
        model.addAttribute("areas", areas);
        model.addAttribute("cuisines", cuisines);

        model.addAttribute("search", searchTerm);
        model.addAttribute("selectedArea", selectedArea);
        model.addAttribute("selectedCuisine", selectedCuisine);

        return "restaurants";
    }
}
