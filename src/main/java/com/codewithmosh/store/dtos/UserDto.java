package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder()
public class UserDto {
    public Long id;
    public String name;
    public String email;
}
