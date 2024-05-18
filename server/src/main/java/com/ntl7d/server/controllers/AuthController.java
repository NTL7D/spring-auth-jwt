package com.ntl7d.server.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntl7d.server.dto.ApiResponse;
import com.ntl7d.server.dto.requests.LoginRequest;
import com.ntl7d.server.dto.requests.RegisterRequest;
import com.ntl7d.server.dto.responses.AuthResponse;
import com.ntl7d.server.services.AuthService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        ApiResponse<AuthResponse> response = new ApiResponse<AuthResponse>(
                true, null, authService.login(request));

        return response;
    }

    @PostMapping("register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest request) {
        ApiResponse<AuthResponse> response = new ApiResponse<AuthResponse>(
                true, null, authService.register(request));

        return response;
    }
}
