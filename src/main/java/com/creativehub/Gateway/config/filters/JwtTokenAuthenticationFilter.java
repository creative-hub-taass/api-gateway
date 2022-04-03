package com.creativehub.Gateway.config.filters;

import java.io.IOException;
import java.text.ParseException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creativehub.Gateway.config.JwtAuthenticationConfig;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.apache.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Authenticate requests with header 'Authorization: Bearer jwt-token'.
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationConfig config;

    public JwtTokenAuthenticationFilter(JwtAuthenticationConfig config) {
        this.config = config;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rsp, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println(config.getSecret());
        String token = req.getHeader(config.getHeader());
        if (req.getServletPath().contains("api/v1/auth/access"))
            filterChain.doFilter(req, rsp);
        else if (token != null && token.startsWith(config.getPrefix() + " ")) {
            token = token.replace(config.getPrefix() + " ", "");
            if (checkToken(token))
                filterChain.doFilter(req, rsp);
            else rsp.sendError(HttpStatus.SC_UNAUTHORIZED);
        }else rsp.sendError(HttpStatus.SC_UNAUTHORIZED);

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