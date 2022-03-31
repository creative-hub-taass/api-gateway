package com.creativehub.Gateway.services.impl;

import com.creativehub.Gateway.services.LoginService;
import com.creativehub.Gateway.services.dto.UserDto;
import com.creativehub.Gateway.util.AuthenticationToken;
import com.creativehub.Gateway.util.JwtUtil;
import com.nimbusds.jose.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class LoginServiceImpl implements LoginService {

    @Override
    public Pair<UserDto, HttpHeaders> login(UserDto user, List<String> roles) throws AuthenticationException {
        String accessToken = JwtUtil.createAccessToken(user.getUsername(), roles);
        String refreshToken = JwtUtil.createRefreshToken(user.getUsername(), roles);
        if (accessToken != null && refreshToken != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-ACCESS-TOKEN", accessToken);
            headers.add("X-REFRESH-TOKEN", refreshToken);
            return Pair.of(user, headers);
        } else throw new AuthenticationServiceException("Cannot create JWT tokens");
    }

    @Override
    public ResponseEntity<String> refresh(String token) {
        AuthenticationToken authenticationToken = JwtUtil.parseToken(token);
        if (authenticationToken != null) {
            String accessToken = JwtUtil.createAccessToken(authenticationToken.getPrincipal(), authenticationToken.getRoles());
            return ResponseEntity.ok(accessToken);
        } else return ResponseEntity.badRequest().body("Invalid refresh token");
    }


}
