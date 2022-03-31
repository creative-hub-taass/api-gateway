package com.creativehub.Gateway.config.filters;

import com.creativehub.Gateway.util.AuthenticationToken;
import com.creativehub.Gateway.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessPath = "/api/v1/access";
        if (request.getServletPath().contains(accessPath)) {
            filterChain.doFilter(request, response);
        } else try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null) {
                if (authorizationHeader.startsWith("Bearer ")) {
                    String token = authorizationHeader.substring("Bearer ".length());
                    AuthenticationToken authenticationToken = JwtUtil.parseToken(token);
                    if (authenticationToken != null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        filterChain.doFilter(request, response);
                    } else throw new IllegalStateException("Invalid authorization token");
                } else throw new IllegalStateException("Invalid authorization token");
            } else throw new IllegalStateException("Missing authorization token");
        } catch (IllegalStateException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            new ObjectMapper().writeValue(response.getOutputStream(), e.getMessage());
        }
    }
}
