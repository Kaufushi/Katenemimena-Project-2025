package com.gr.hua.dit.project2025.StreetFoodGo.web.api;

import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.RestaurantDto;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    public RestaurantApiController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantDto> getRestaurants(
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String cuisine
    ) {
        return restaurantService.findRestaurants(area, cuisine);
    }
}
