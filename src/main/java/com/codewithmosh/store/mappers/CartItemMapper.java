package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
//    @Mapping(source = "product.category.id", target = "product.categoryId")
    @Mapping(target = "totalPrice", expression = "java(getTotalPrice(cartItem))")
    CartItemDto toDto(CartItem cartItem);

    default BigDecimal getTotalPrice(CartItem cartItem) {
        Integer quantity = cartItem.getQuantity();
        BigDecimal unitPrice = cartItem.getProduct().getPrice();
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}
