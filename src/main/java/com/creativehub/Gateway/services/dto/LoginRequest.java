package com.creativehub.Gateway.services.dto;

import lombok.Data;


@Data
public class LoginRequest {
    private final String email;
    private final String password;
}
