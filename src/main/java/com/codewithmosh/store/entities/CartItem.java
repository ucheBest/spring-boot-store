package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart", nullable = false)
    @ToString.Exclude
    private Cart cart;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    @Setter
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    public CartItem(Product product, Cart cart) {
        addProduct(product);
        addCart(cart);
    }

    public void addProduct(Product product) {
        this.product = product;
        product.getCartItems().add(this);
        this.incrementQuantity();
    }

    public void addCart(Cart cart) {
        this.cart = cart;
        cart.getCartItems().add(this);
    }

    public void incrementQuantity() {
        this.quantity += 1;

    }
}