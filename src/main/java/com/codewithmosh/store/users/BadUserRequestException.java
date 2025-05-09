package com.codewithmosh.store.users;

public class BadUserRequestException extends RuntimeException {
    public BadUserRequestException(String message) {
        super(message);
    }
}
