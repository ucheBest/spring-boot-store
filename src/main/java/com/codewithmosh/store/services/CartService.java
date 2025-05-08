package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.AddProductToCartDtoRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.products.ProductNotFoundException;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.products.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartDto createCart() {
        var newCart = cartRepository.save(new Cart());
        return cartMapper.toDto(newCart);
    }

    public CartItemDto addToCart(UUID cartId, AddProductToCartDtoRequest request) {
        var cart = cartRepository.getCartWithItems(cartId)
            .orElseThrow(CartNotFoundException::new);

        var product = productRepository.findById(request.getProductId())
            .orElseThrow(ProductNotFoundException::new);

        var cartItem = cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId)
            .orElseThrow(CartNotFoundException::new);

        return cartMapper.toDto(cart);
    }

    public CartDto updateItem(
        UUID cartId,
        Long productId,
        UpdateCartItemRequest request
    ) {
        var cart = cartRepository.getCartWithItems(cartId)
            .orElseThrow(CartNotFoundException::new);

        var cartItem = cart.getItem(productId);

        if (cartItem == null) {
            throw new ProductNotFoundException();
        }
        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public void removeCartItem(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId)
            .orElseThrow(CartNotFoundException::new);

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void removeAllCartItems(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId)
            .orElseThrow(CartNotFoundException::new);

        cart.removeAllItems();
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId)
            .orElseThrow(CartNotFoundException::new);

        clearCart(cart);
    }

    public void clearCart(Cart cart) {
        cart.removeAllItems();
    }
}
