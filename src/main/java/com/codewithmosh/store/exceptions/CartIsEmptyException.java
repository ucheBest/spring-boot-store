package com.codewithmosh.store.exceptions;

public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException() {
        super("Cart is empty");
    }
}
