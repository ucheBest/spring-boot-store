package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "id", expression = "java(cart.getId().toString())")
    CartDto toCartDto(Cart cart);

    @Mapping(target = "items", expression = "java(getCartItems(cartItemDtos))")
    @Mapping(target = "totalPrice", dependsOn = {"items"}, expression = "java(getTotalCartPrice(cartDto.getItems()))")
    CartDto toCartDto(Cart cart, List<CartItemDto> cartItemDtos);

    default BigDecimal getTotalCartPrice(List<CartItemDto> items) {
        return items.stream()
                .map(CartItemDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default List<CartItemDto> getCartItems(List<CartItemDto> cartItems) {
        return cartItems;
    }
}
