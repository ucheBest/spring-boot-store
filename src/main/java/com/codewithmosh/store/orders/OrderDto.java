package com.codewithmosh.store.orders;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class OrderDto {
    private Long id;
    private String status;
    private LocalDateTime createdAt;
    @Builder.Default
    private List<OrderItemDto> orderItems = new ArrayList<>();
    private BigDecimal totalPrice;
}
