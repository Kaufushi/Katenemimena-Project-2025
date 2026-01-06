package com.gr.hua.dit.project2025.StreetFoodGo.core.repository;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.MenuItem;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurant(Restaurant restaurant);

    List<MenuItem> findByRestaurantAndAvailableTrue(Restaurant restaurant);

    Optional<MenuItem> findByNameAndRestaurant(String name, Restaurant restaurant);

}
