package com.codewithmosh.store.services;

import com.codewithmosh.store.UserModel;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("User not found"));
        var userModel = new UserModel(String.valueOf(user.getId()), user.getPassword(), Collections.emptyList());
        userModel.setNameOfUser(user.getName());
        userModel.setEmail(user.getEmail());
        return userModel;
    }
}
