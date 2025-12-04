package com.gr.hua.dit.project2025.StreetFoodGo.core.repository;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.OrderItem;
import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Find all items belonging to a specific order
    List<OrderItem> findByOrder(Order order);
}