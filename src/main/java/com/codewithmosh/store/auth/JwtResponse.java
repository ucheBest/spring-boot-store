package com.codewithmosh.store.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private String token;
}
