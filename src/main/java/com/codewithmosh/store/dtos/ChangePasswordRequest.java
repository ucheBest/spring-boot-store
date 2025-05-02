package com.codewithmosh.store.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
