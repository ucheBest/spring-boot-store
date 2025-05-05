package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCartItemRequest {
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be between 1 to 100" )
    @Max(value = 100, message = "Quantity must be between 1 to 100")
    private Integer quantity;
}
