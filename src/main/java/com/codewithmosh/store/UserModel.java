package com.codewithmosh.store;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class UserModel extends User {
    private String nameOfUser;
    private String email;

    public UserModel(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
        super(userId, password, authorities);
    }

    public String getUserId() {
        return getUsername();
    }

}
