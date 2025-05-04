package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.CartItem;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CartDto {
    private String id;
    private final CartItem[] items = new CartItem[0];
    private final BigDecimal totalPrice = BigDecimal.valueOf(0);
}
