package com.sangpt.teamchatspring.middleware;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sangpt.teamchatspring.utill.JwtUtill;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    private final JwtUtill jwtUtill;

    public JwtAuthenticationInterceptor(JwtUtill jwtUtill) {
        this.jwtUtill = jwtUtill;
    }

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            if (jwtUtill.validateToken(token, request)) {
                // String username = jwtUtill.getUsernameFromToken(token);

                return true;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
