package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private Set<OrderItem> orderItems = new LinkedHashSet<>();


    public static Order fromCart(Cart cart, User customer) {
        var order = Order.builder()
            .customer(customer)
            .status(OrderStatus.PENDING)
            .totalPrice(cart.getTotalPrice())
            .build();

        Set<OrderItem> orderItems = cart.getCartItems().stream()
            .map(item -> OrderItem.builder()
                .order(order)
                .product(item.getProduct())
                .unitPrice(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .build())
            .collect(Collectors.toUnmodifiableSet());

        order.orderItems.addAll(orderItems);

        return order;
    }

    public BigDecimal getTotalPrice() {
        return this.orderItems.stream()
            .map(OrderItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isPlacedBy(User customer) {
        return this.customer.equals(customer);
    }

}