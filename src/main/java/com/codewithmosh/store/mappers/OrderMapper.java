package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.CheckoutResponse;
import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderId", source = "id")
    CheckoutResponse toCheckoutDto(Order order);

    @Mapping(target = "totalPrice", expression = "java(order.getTotalPrice())")
    OrderDto toDto(Order order);
}
