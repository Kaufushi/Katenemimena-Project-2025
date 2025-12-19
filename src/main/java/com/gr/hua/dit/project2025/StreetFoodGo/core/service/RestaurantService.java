package com.gr.hua.dit.project2025.StreetFoodGo.core.service;

import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.RestaurantDto;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Restaurant;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantDto> findRestaurants(String area, String cuisine) {
        return restaurantRepository.findAll()
                .stream()
                .filter(r -> area == null || area.isBlank() || r.getArea().equalsIgnoreCase(area))
                .filter(r -> cuisine == null || cuisine.isBlank() || r.getCuisine().equalsIgnoreCase(cuisine))
                .map(this::toDto)
                .toList();
    }

    private RestaurantDto toDto(Restaurant r) {
        return new RestaurantDto(
                r.getId(),
                r.getName(),
                r.getArea(),
                r.getCuisine(),
                r.getImageUrl()
        );
    }
}
