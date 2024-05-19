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

        public AuthResponse login(LoginRequest request) {
                authManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

                User user = userRepository.findByUsername(request.username()).orElseThrow();

                return new AuthResponse(jwtService.getToken(user));
        }

        public AuthResponse register(RegisterRequest request) {
                User user = userMapper.toRegister(request);
                user.setPassword(passwordEncoder.encode(request.password()));
                user.setRole(Role.USER);

                userRepository.save(user);

                return new AuthResponse(jwtService.getToken(user));
        }

}
