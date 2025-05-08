package com.codewithmosh.store.dtos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemDto {
    private OrderProductDto product;
    private int quantity;
    private final BigDecimal totalPrice;
}
