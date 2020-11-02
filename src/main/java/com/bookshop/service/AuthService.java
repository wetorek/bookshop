package com.bookshop.service;

import com.bookshop.Util.Constants;
import com.bookshop.controller.dto.NotificationEmail;
import com.bookshop.controller.dto.RegisterRequest;
import com.bookshop.entity.User;
import com.bookshop.entity.VerificationToken;
import com.bookshop.mapper.UserMapper;
import com.bookshop.repository.UserRepository;
import com.bookshop.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = userMapper.mapRegisterRequestToUser(registerRequest, passwordEncoder);
        userRepository.save(user);
        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Thank You for creating account, click below to activate Your account:\n" + Constants.ACTIVATION_MAIL + "/" + token);
        mailService.sendMail(new NotificationEmail("Please activate Your account", user.getEmail(), message));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Transactional
    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow();
        fetchUserAndEnable(verificationToken);
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setEnabled(true);
        userRepository.save(user);
    }
}
