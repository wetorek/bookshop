package com.bookshop.controller;

import com.bookshop.controller.dto.AuthenticationResponse;
import com.bookshop.controller.dto.LoginRequest;
import com.bookshop.controller.dto.RefreshTokenRequest;
import com.bookshop.controller.dto.RegisterRequest;
import com.bookshop.service.AuthService;
import com.bookshop.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public String signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return "Registered successfully, please check Your email";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("accountVerification/{token}")
    public String verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return "Account created successfully";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    public String logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return "Refresh token deleted!";
    }
}
