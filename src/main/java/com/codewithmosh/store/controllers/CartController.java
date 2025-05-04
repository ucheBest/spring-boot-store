package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.AddProductToCartDtoRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartRequest;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import com.codewithmosh.store.mappers.CartItemMapper;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartItemRepository;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    public CartController(
            CartRepository cartRepository,
            CartMapper cartMapper,
            ProductRepository productRepository,
            CartItemRepository cartItemRepository,
            CartItemMapper cartItemMapper
    ) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    @PostMapping
    public ResponseEntity<CartDto> registerCart(
            UriComponentsBuilder uriBuilder
    ) {
        var newCart = cartRepository.save(new Cart());
        CartDto cartDto = cartMapper.toCartDto(newCart);
        URI uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCart(@PathVariable String cartId) {
        var cart = cartRepository.findById(UUID.fromString(cartId)).orElse(null);
        if (cart == null) {
            return new ResponseEntity<>(Map.of("cart", "Cart doesn't exist"), HttpStatus.NOT_FOUND);
        }
        List<CartItemDto> cartItems = cart.getCartItems()
                .stream()
                .map(cartItemMapper::toDto)
                .toList();

        CartDto cartDto = cartMapper.toCartDto(cart, cartItems);
        return ResponseEntity.ok().body(cartDto);
    }

    @PostMapping("{cartId}/items")
    public ResponseEntity<?> addProductToCart(
            @PathVariable String cartId,
            @Valid @RequestBody AddProductToCartDtoRequest request
    ) {
        var cart = cartRepository.findById(UUID.fromString(cartId)).orElse(null);
        if (cart == null) {
            return new ResponseEntity<>(Map.of("cart", "Cart doesn't exist"), HttpStatus.NOT_FOUND);
        }

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("product", "Product doesn't exist")
            );
        }
        var existingCartItem = cartItemRepository.
                findByCartEqualsAndProductEquals(cart, product).orElse(null);

        CartItem cartItem;
        if (existingCartItem == null) {
            cartItem = new CartItem(product, cart);
        } else {
            cartItem = existingCartItem;
            cartItem.incrementQuantity();
        }
        cartItemRepository.save(cartItem);
        var cartItemDto = cartItemMapper.toDto(cartItem);
        return ResponseEntity.ok().body(cartItemDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCart(
            @PathVariable String cartId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartRequest request
    ) {
        var cart = cartRepository.findById(UUID.fromString(cartId)).orElse(null);
        if (cart == null) {
            return new ResponseEntity<>(Map.of("cart", "Cart doesn't exist"), HttpStatus.NOT_FOUND);
        }

        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("product", "Product doesn't exist")
            );
        }
        var cartItem = cartItemRepository.
                findByCartEqualsAndProductEquals(cart, product).orElse(null);
        if (cartItem == null) {
            return new ResponseEntity<>(Map.of("cart", "Cart doesn't exist"), HttpStatus.NOT_FOUND);
        }
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        return ResponseEntity.ok().body(cartItemMapper.toDto(cartItem));
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCart(
            @PathVariable String cartId,
            @PathVariable Long productId
    ) {
        var cart = cartRepository.findById(UUID.fromString(cartId)).orElse(null);
        if (cart == null) {
            return new ResponseEntity<>(Map.of("cart", "Cart doesn't exist"), HttpStatus.NOT_FOUND);
        }

        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("product", "Product doesn't exist")
            );
        }
        var cartItem = cartItemRepository.
                findByCartEqualsAndProductEquals(cart, product).orElse(null);
        if (cartItem == null) {
            return new ResponseEntity<>(Map.of("cart", "Cart doesn't exist"), HttpStatus.NOT_FOUND);
        }
        cartItemRepository.delete(cartItem);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> deleteCart(
            @PathVariable String cartId
    ) {
        var cart = cartRepository.findById(UUID.fromString(cartId)).orElse(null);
        if (cart == null) {
            return new ResponseEntity<>(Map.of("cart", "Cart doesn't exist"), HttpStatus.NOT_FOUND);
        }
        cartRepository.delete(cart);
        return ResponseEntity.noContent().build();
    }
}
