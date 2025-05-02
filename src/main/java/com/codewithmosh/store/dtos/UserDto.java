package com.codewithmosh.store.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder()
public class UserDto {
    @JsonIgnore
    private Long id;
    @JsonProperty("first_name")
    private String name;
    private String email;
}
