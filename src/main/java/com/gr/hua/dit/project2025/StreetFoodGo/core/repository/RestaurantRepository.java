package com.gr.hua.dit.project2025.StreetFoodGo.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    List<Restaurant> findByName(String name);
    List<Restaurant> findByCuisine(String cuisine);
    List<Restaurant> findByArea(String area);


    List<Restaurant> findByNameContainingIgnoreCaseAndAreaIgnoreCaseAndCuisineIgnoreCase(
            String name, String area, String cuisine
    );
}
