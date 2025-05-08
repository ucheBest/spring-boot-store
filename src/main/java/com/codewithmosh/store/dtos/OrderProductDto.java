package com.codewithmosh.store.dtos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
