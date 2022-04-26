package com.creativehub.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

/**
 * Config JWT
 */
@Getter
public class JwtAuthenticationConfig {
	@Value("${security.jwt.urlAuth}")
	private String urlAuth;

	@Value("${security.jwt.urlHome}")
	private String urlHome;

	@Value("${security.jwt.header}")
	private String header;

	@Value("${security.jwt.prefix}")
	private String prefix;

	@Value("${security.jwt.secret}")
	private String secret;
}