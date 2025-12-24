package com.gr.hua.dit.project2025.StreetFoodGo.web;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Restaurant;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.RestaurantRepository;
import com.gr.hua.dit.project2025.StreetFoodGo.security.PersonDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    @GetMapping("/restaurants/new")
    public String showCreateRestaurantForm() {
        return "newRestaurant";
    }

    @PostMapping("/restaurants/new")
    public String createRestaurant(
            @RequestParam String name,
            @RequestParam String area,
            @RequestParam String cuisine,
            @RequestParam String imageUrl,
            @RequestParam String email,
            @RequestParam String telephone,
            @AuthenticationPrincipal PersonDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {

        Long phone;
        try {
            phone = Long.parseLong(telephone);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Telephone must be a valid number"
            );
            return "redirect:/restaurants/new";
        }

        Person owner = userDetails.getPerson();

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setArea(area);
        restaurant.setCuisine(cuisine);
        restaurant.setImageUrl(imageUrl);
        restaurant.setEmail(email);
        restaurant.setTelephone(phone);
        restaurant.setOwner(owner);

        restaurantRepository.save(restaurant);

        redirectAttributes.addFlashAttribute(
                "success",
                "Restaurant added successfully!"
        );

        return "redirect:/restaurants";
    }

    @GetMapping("/restaurants/{id}")
    public String showRestaurant(
            @PathVariable Long id,
            Model model
    ) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        model.addAttribute("restaurant", restaurant);

        return "restaurant";
    }


}
