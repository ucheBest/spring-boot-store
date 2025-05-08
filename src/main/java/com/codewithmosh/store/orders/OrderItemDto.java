package com.codewithmosh.store.orders;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemDto {
    private ProductDto product;
    private int quantity;
    private final BigDecimal totalPrice;
}
