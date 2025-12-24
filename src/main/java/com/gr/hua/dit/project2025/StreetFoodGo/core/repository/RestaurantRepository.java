package com.gr.hua.dit.project2025.StreetFoodGo.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    List<Restaurant> findByAreaIgnoreCase(String area);

    List<Restaurant> findByCuisineIgnoreCase(String cuisine);

    List<Restaurant> findByNameContainingIgnoreCaseAndAreaIgnoreCaseAndCuisineIgnoreCase(
            String name, String area, String cuisine
    );

    List<Restaurant> findByNameContainingIgnoreCaseAndAreaIgnoreCase(
            String name, String area
    );

    List<Restaurant> findByNameContainingIgnoreCaseAndCuisineIgnoreCase(
            String name, String cuisine
    );

    List<Restaurant> findByAreaIgnoreCaseAndCuisineIgnoreCase(
            String area, String cuisine
    );

}
