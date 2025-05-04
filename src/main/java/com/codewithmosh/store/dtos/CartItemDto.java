package com.codewithmosh.store.dtos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CartItemDto {
    private ProductDto product;
    private int quantity;
    private final BigDecimal totalPrice;
}
