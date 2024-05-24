package com.ntl7d.server.configs;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ntl7d.server.services.JwtService;
import com.ntl7d.server.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthFilter extends OncePerRequestFilter {

        JwtService jwtService;
        UserDetailsService userDetailsService;
        CookieUtils cookieUtils;

        @SuppressWarnings("null")
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                        FilterChain filterChain) throws ServletException, IOException {

                final String token = getTokenFromRequest(request);
                final String username;

                if (token == null) {
                        filterChain.doFilter(request, response);
                        return;
                }

                username = jwtService.getUsernameFromToken(token);

                if (username != null
                                && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        if (jwtService.isTokenValid(token, userDetails)) {
                                UsernamePasswordAuthenticationToken authToken =
                                                new UsernamePasswordAuthenticationToken(username,
                                                                null, userDetails.getAuthorities());

                                authToken.setDetails(new WebAuthenticationDetailsSource()
                                                .buildDetails(request));

                                SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                }

                filterChain.doFilter(request, response);
        }

        private String getTokenFromRequest(HttpServletRequest request) {
                return cookieUtils.readCookie("accessToken", request);
        }
}
