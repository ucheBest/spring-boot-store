package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.CheckoutRequest;
import com.codewithmosh.store.dtos.ErrorDto;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
import com.codewithmosh.store.entities.OrderStatus;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.UserRepository;
import com.codewithmosh.store.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class CheckoutController {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> checkout(
        @Valid @RequestBody CheckoutRequest request
    ) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            return ResponseEntity.badRequest().body(
                new ErrorDto("Cart not found")
            );
        }
        if (cart.getCartItems().isEmpty()) {
            return ResponseEntity.badRequest().body(
                new ErrorDto("Cart is empty")
            );
        }

        var user = authService.getCurrentUser();
        if (user == null) {
            return new ResponseEntity<>(
                new ErrorDto("user nor found"), HttpStatus.NOT_FOUND);
        }

        var order = Order.builder()
            .status(OrderStatus.PENDING)
            .totalPrice(cart.getTotalPrice())
            .createdAt(LocalDateTime.now())
            .customer(user)
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
        order.addItems(orderItems);

        var savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return ResponseEntity.ok(orderMapper.toDto(savedOrder));

    }
}
