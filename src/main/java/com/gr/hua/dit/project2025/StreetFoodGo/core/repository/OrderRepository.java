package com.gr.hua.dit.project2025.StreetFoodGo.core.repository;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Order;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    // Find orders by restaurant ID with eager fetching to avoid lazy loading issues
    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.customer " +
            "LEFT JOIN FETCH o.restaurant " +
            "LEFT JOIN FETCH o.orderItems oi " +
            "LEFT JOIN FETCH oi.menuItem " +
            "WHERE o.restaurant.id = :restaurantId " +
            "ORDER BY o.createdAt DESC")
    List<Order> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    // Find orders by customer with eager fetching
    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.customer " +
            "LEFT JOIN FETCH o.restaurant " +
            "LEFT JOIN FETCH o.orderItems oi " +
            "LEFT JOIN FETCH oi.menuItem " +
            "WHERE o.customer = :customer " +
            "ORDER BY o.createdAt DESC")
    List<Order> findByCustomerOrderByCreatedAtDesc(@Param("customer") Person customer);
}