package com.creativehub.Gateway.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * Config JWT.
 * Only one property 'shuaicj.security.jwt.secret' is mandatory.
 */
@Getter
@ToString
public class JwtAuthenticationConfig {

    @Value("${temp.security.jwt.url:/api/v1/auth/access/**}")
    private String url;

    @Value("${temp.security.jwt.header:Authorization}")
    private String header;

    @Value("${temp.security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${temp.security.jwt.expiration:#{10}}")
    private int expiration; // 10 minutes

    @Value("${temp.security.jwt.secret}")
    private String secret;
}