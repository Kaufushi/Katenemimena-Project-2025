package com.gr.hua.dit.project2025.StreetFoodGo.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createdAt = Instant.now();

    @Enumerated(EnumType.STRING)
    private Status status;

    // Customer who placed the order
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Person customer;

    // Store from which the order is placed
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Restaurant restaurant;

    private double TotalPrice;

    // Items in the order
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;




    public enum Status {
        PENDING, PREPARING, IN_DELIVERY, COMPLETED, CANCELLED
    }
}
