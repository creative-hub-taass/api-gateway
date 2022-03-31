package com.creativehub.Gateway.services;

import com.creativehub.Gateway.services.dto.UserDto;
import com.nimbusds.jose.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public interface LoginService {

    ResponseEntity<String> refresh(String token);

    Pair<UserDto, HttpHeaders> login(UserDto user, List<String> roles) throws AuthenticationException;

}
