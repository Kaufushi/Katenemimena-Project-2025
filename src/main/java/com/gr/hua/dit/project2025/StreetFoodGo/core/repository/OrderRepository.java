package com.gr.hua.dit.project2025.StreetFoodGo.core.repository;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Order;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Person;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find all orders placed by a specific customer
    List<Order> findByCustomer(Person customer);

    // Find all orders for a specific restaurant
    List<Order> findByRestaurant(Restaurant restaurant);

    // Find all orders by status
    List<Order> findByStatus(Order.Status status);

    // Optional: find orders by customer and status
    List<Order> findByCustomerAndStatus(Person customer, Order.Status status);
}
