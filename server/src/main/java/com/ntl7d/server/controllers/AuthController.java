package com.ntl7d.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntl7d.server.dto.ApiResponse;
import com.ntl7d.server.dto.requests.LoginRequest;
import com.ntl7d.server.dto.requests.RegisterRequest;
import com.ntl7d.server.dto.responses.AuthResponse;
import com.ntl7d.server.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request,
            HttpServletResponse httpResponse) {
        AuthResponse auth = authService.login(request, httpResponse);
        ApiResponse<AuthResponse> response = new ApiResponse<>();

        response.setSuccess(true);
        response.setResult(auth);

        return response;
    }

    @PostMapping("register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest request,
            HttpServletResponse httpResponse) {
        ApiResponse<AuthResponse> response = new ApiResponse<>();

        response.setSuccess(true);
        response.setResult(authService.register(request, httpResponse));

        return response;
    }

    @GetMapping("refresh")
    public ApiResponse<AuthResponse> refreshh(HttpServletRequest httpRequest) {

        ApiResponse<AuthResponse> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setResult(authService.refresh(httpRequest));

        return response;
    }


    @PostMapping("logout")
    public ApiResponse<AuthResponse> logout(HttpServletResponse httpResponse) {
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        authService.logout(httpResponse);

        response.setSuccess(true);
        response.setMessage("Logout successful");

        return response;
    }



}
