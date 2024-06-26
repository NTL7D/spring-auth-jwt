package com.ntl7d.server.utils;

import org.springframework.stereotype.Component;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtils {
    public void setJwtCookie(String token, HttpServletResponse httpResponse) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);

        httpResponse.addCookie(cookie);
    }

    public String readCookie(String name, HttpServletRequest httpRequest) {
        Cookie[] cookie = httpRequest.getCookies();
        if (cookie != null) {
            for (Cookie cookies : cookie) {
                if (cookies.getName().equals(name)) {
                    return cookies.getValue();
                }
            }
        }
        return null;
    }

    public void clearCookie(String name, String path, HttpServletResponse httpResponse) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(path);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);

        httpResponse.addCookie(cookie);
    }
}
