package com.codewithmosh.store.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "totalPrice", expression = "java(order.getTotalPrice())")
    OrderDto toDto(Order order);
}
