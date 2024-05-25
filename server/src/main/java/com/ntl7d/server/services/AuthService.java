package com.ntl7d.server.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ntl7d.server.dto.requests.LoginRequest;
import com.ntl7d.server.dto.requests.RegisterRequest;
import com.ntl7d.server.dto.responses.AuthResponse;
import com.ntl7d.server.mappers.UserMapper;
import com.ntl7d.server.models.Role;
import com.ntl7d.server.models.User;
import com.ntl7d.server.repositories.UserRepository;
import com.ntl7d.server.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

        UserRepository userRepository;
        JwtService jwtService;
        PasswordEncoder passwordEncoder;
        AuthenticationManager authManager;
        UserMapper userMapper;
        CookieUtils cookieUtils;


        public AuthResponse login(LoginRequest request, HttpServletResponse httpResponse) {
                authManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(),
                                request.password()));

                User user = userRepository.findByUsername(request.username()).orElseThrow();

                String accessToken = jwtService.generateAccessToken(user);
                String refreshToken = jwtService.generateRefreshToken(user);

                cookieUtils.setAccessToken(accessToken, httpResponse);
                cookieUtils.setRefreshToken(refreshToken, httpResponse);

                return new AuthResponse(accessToken, refreshToken);
        }

        public AuthResponse register(RegisterRequest request, HttpServletResponse httpResponse) {
                User user = userMapper.toRegister(request);
                user.setPassword(passwordEncoder.encode(request.password()));
                user.setRole(Role.USER);

                userRepository.save(user);

                String accessToken = jwtService.generateAccessToken(user);
                String refreshToken = jwtService.generateRefreshToken(user);

                cookieUtils.setAccessToken(accessToken, httpResponse);
                cookieUtils.setRefreshToken(refreshToken, httpResponse);

                return new AuthResponse(accessToken, refreshToken);
        }

        public AuthResponse refresh(HttpServletRequest httpRequest) {
                String refreshToken = cookieUtils.readCookie("refreshToken", httpRequest);

                String username = jwtService.getUsernameFromToken(refreshToken);
                User user = userRepository.findByUsername(username).orElseThrow();

                if (jwtService.isTokenValid(refreshToken, user)) {
                        String accessToken = jwtService.generateAccessToken(user);
                        return new AuthResponse(accessToken, refreshToken);
                }

                return null;
        }

        public void logout(HttpServletResponse httpResponse) {
                cookieUtils.clearCookie("accessToken", "/", httpResponse);
                cookieUtils.clearCookie("refreshToken", "/api/v1/auth/refresh", httpResponse);
        }


}
