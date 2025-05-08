package com.codewithmosh.store.users;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserRequest {
    private String name;
    private String email;
}
