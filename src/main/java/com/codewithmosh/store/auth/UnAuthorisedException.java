package com.codewithmosh.store.auth;

public class UnAuthorisedException extends RuntimeException {
    public UnAuthorisedException() {
        super("Unauthorised");
    }

    public UnAuthorisedException(String message) {
        super(message);
    }
}
