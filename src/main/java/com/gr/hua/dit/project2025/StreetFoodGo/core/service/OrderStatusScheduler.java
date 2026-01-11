package com.gr.hua.dit.project2025.StreetFoodGo.core.service;

import com.gr.hua.dit.project2025.StreetFoodGo.core.model.Order;
import com.gr.hua.dit.project2025.StreetFoodGo.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusScheduler {

    private final OrderRepository orderRepository;

    @Value("${order.status.pending-to-preparing-minutes:2}")
    private int pendingToPreparingMinutes;

    @Value("${order.status.preparing-to-delivery-minutes:2}")
    private int preparingToDeliveryMinutes;

    @Value("${order.status.delivery-to-completed-minutes:2}")
    private int deliveryToCompletedMinutes;

    @Transactional
    @Scheduled(fixedRate = 60000) // runs every 1 minute
    public void updateOrderStatuses() {

        List<Order> orders = orderRepository.findByStatusIn(
                List.of(
                        Order.Status.PENDING,
                        Order.Status.PREPARING,
                        Order.Status.IN_DELIVERY
                )
        );

        if (orders.isEmpty()) {
            return;
        }

        log.debug("Processing {} orders for status updates", orders.size());
        int updatedCount = 0;

        for (Order order : orders) {
            try {
                if (updateOrderStatus(order)) {
                    updatedCount++;
                }
            } catch (Exception e) {
                log.error("Failed to update order {}: {}", order.getId(), e.getMessage(), e);
            }
        }

        if (updatedCount > 0) {
            orderRepository.saveAll(orders);
            log.info("Updated {} out of {} orders", updatedCount, orders.size());
        }
    }

    private boolean updateOrderStatus(Order order) {
        // Handle orders without statusUpdatedAt (legacy data)
        if (order.getStatusUpdatedAt() == null) {
            order.setStatusUpdatedAt(Instant.now());
            log.warn("Order {} had null statusUpdatedAt, initialized to now", order.getId());
            return true; // Return true to ensure this gets saved
        }

        long minutes = Duration.between(order.getStatusUpdatedAt(), Instant.now()).toMinutes();
        boolean updated = false;

        switch (order.getStatus()) {
            case PENDING -> {
                if (minutes >= pendingToPreparingMinutes) {
                    order.setStatus(Order.Status.PREPARING);
                    order.setStatusUpdatedAt(Instant.now());
                    log.debug("Order {} transitioned from PENDING to PREPARING", order.getId());
                    updated = true;
                }
            }

            case PREPARING -> {
                if (minutes >= preparingToDeliveryMinutes) {
                    order.setStatus(Order.Status.IN_DELIVERY);
                    order.setStatusUpdatedAt(Instant.now());
                    log.debug("Order {} transitioned from PREPARING to IN_DELIVERY", order.getId());
                    updated = true;
                }
            }

            case IN_DELIVERY -> {
                if (minutes >= deliveryToCompletedMinutes) {
                    order.setStatus(Order.Status.COMPLETED);
                    order.setStatusUpdatedAt(Instant.now());
                    log.debug("Order {} transitioned from IN_DELIVERY to COMPLETED", order.getId());
                    updated = true;
                }
            }

            case COMPLETED, CANCELLED -> {
                // do nothing
            }
        }

        return updated;
    }
}