package com.codewithmosh.store.carts;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
