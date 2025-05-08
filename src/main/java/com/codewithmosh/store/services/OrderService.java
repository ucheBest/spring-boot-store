package com.codewithmosh.store.services;

import com.codewithmosh.store.controllers.OrderRepository;
import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.exceptions.OrderNotFoundException;
import com.codewithmosh.store.mappers.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.findAllCustomerOrdersWithItems(user);
        return orders.stream()
            .map(orderMapper::toDto)
            .toList();
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository.findOrderWithItems(orderId)
            .orElseThrow(OrderNotFoundException::new);

        var user = authService.getCurrentUser();
        if (!order.isPlacedBy(user)) {
            throw new AccessDeniedException("You are not authorized to access this order.");
        }

        return orderMapper.toDto(order);
    }
}
