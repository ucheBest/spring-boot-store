package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.AddProductToCartDtoRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import com.codewithmosh.store.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ) {
        CartDto cartDto = cartService.createCart();
        URI uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable UUID cartId) {
        return cartService.getCart(cartId);
    }

    @PostMapping("{cartId}/items")
    @Operation(summary = "Adds a product to the cart")
    public ResponseEntity<CartItemDto> addToCart(
            @Parameter(description = "The ID of the cart") @PathVariable UUID cartId,
            @Valid @RequestBody AddProductToCartDtoRequest request
    ) {
        CartItemDto cartItemDto = cartService.addToCart(cartId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartDto updateItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {
        return cartService.updateItem(cartId, productId, request);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId
    ) {
        cartService.removeCartItem(cartId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> deleteCart(
            @PathVariable UUID cartId
    ) {
        cartService.removeAllCartItems(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlerCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("cart", "Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlerProductNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("product", "Product not found in the cart"));
    }
}
