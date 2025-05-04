package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartController(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @PostMapping
    public ResponseEntity<CartDto> registerCart() {
        var newCart = cartRepository.save(new Cart());
        CartDto cartDto = cartMapper.toCartDto(newCart);
        return ResponseEntity.ok().body(cartDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String cartId) {
        var cart = cartRepository.findById(UUID.fromString(cartId)).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        CartDto cartDto = cartMapper.toCartDto(cart);
        return ResponseEntity.ok().body(cartDto);
    }
}
