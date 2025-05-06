package com.codewithmosh.store.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private String token;
}
