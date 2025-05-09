package com.codewithmosh.store.auth;

import jakarta.servlet.http.Cookie;

public record AuthResponse(Cookie cookie, String accessToken) {
}
