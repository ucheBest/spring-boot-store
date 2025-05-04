package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddProductToCartDtoRequest {
    @NotNull(message = "productId must not be null")
    private Long productId;
}
