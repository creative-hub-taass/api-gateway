package com.creativehub.backend.config.filters;

import com.creativehub.backend.config.JwtAuthenticationConfig;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * Authenticate requests with header 'Authorization: Bearer jwt-token'.
 */
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	private final JwtAuthenticationConfig config;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rsp, FilterChain filterChain) throws ServletException, IOException {
		String token = req.getHeader(config.getHeader());
		if (req.getServletPath().contains(config.getUrl())) {
			filterChain.doFilter(req, rsp);
		} else if (token != null && token.startsWith(config.getPrefix() + " ")) {
			token = token.replace(config.getPrefix() + " ", "");
			if (checkToken(token)) {
				filterChain.doFilter(req, rsp);
			} else rsp.sendError(HttpStatus.SC_UNAUTHORIZED);
		} else rsp.sendError(HttpStatus.SC_UNAUTHORIZED);
	}

	private boolean checkToken(String token) {
		try {
			byte[] secretKey = config.getSecret().getBytes();
			SignedJWT signedJWT = SignedJWT.parse(token);
			return signedJWT.verify(new MACVerifier(secretKey));
		} catch (JOSEException | ParseException e) {
			System.err.println("Error auth token: " + token);
		}
		return false;
	}
}