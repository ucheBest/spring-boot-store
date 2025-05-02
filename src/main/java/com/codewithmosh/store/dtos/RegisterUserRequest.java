package com.codewithmosh.store.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterUserRequest {
    private String name;
    private String password;
    private String email;
}
