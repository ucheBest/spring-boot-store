package com.codewithmosh.store.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartDto {
    private String id;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice;
}
