package com.codewithmosh.store.services;

import com.codewithmosh.store.controllers.OrderRepository;
import com.codewithmosh.store.dtos.CheckoutRequest;
import com.codewithmosh.store.dtos.CheckoutResponse;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.exceptions.CartIsEmptyException;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.UserNotFoundException;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;

    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        if (cart.isEmpty()) {
            throw new CartIsEmptyException();
        }

        var user = authService.getCurrentUser();
        if (user == null) {
            throw new UserNotFoundException();
        }

        var order = Order.fromCart(cart, user);

        var savedOrder = orderRepository.save(order);

        cart.removeAllItems();
        cartRepository.save(cart);

        return orderMapper.toDto(savedOrder);
    }

}
