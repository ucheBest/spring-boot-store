package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        return cartItems.stream()
            .map(CartItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CartItem getItem(Long productId) {
        return this.cartItems.stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(null);
    }

    public CartItem addItem(Product product) {
        var cartItem = this.getItem(product.getId());

        if (cartItem == null) {
            cartItem = new CartItem(product, this);
            cartItems.add(cartItem);
        } else {
            cartItem.incrementQuantity();
        }
        return cartItem;
    }

    public void removeItem(Long productId) {
        var cartItem = this.getItem(productId);
        if (cartItem != null) {
            cartItem.decrementQuantity();
            if (cartItem.getQuantity() == 0) {
                cartItems.remove(cartItem);
            }
        }
    }

    public void removeAllItems() {
        this.cartItems.clear();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}