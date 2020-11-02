package com.bookshop.mapper;

import com.bookshop.controller.dto.RegisterRequest;
import com.bookshop.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User mapRegisterRequestToUser(RegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        User user = modelMapper.map(registerRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setEnabled(false);
        return user;
    }
}
