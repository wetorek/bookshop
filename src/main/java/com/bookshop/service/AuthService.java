package com.bookshop.service;

import com.bookshop.controller.dto.RegisterRequest;
import com.bookshop.entity.User;
import com.bookshop.mapper.UserMapper;
import com.bookshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    @Transactional
    public void signup(RegisterRequest registerRequest) {
            User user = userMapper.mapRegisterRequestToUser(registerRequest, passwordEncoder);
            userRepository.save(user);
    }
}
