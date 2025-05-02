package com.codewithmosh.store.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserRequest {
    private String name;
    private String email;
}
