package com.liga.carwash.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liga.carwash.enums.TokenType;
import com.liga.carwash.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.access_time}")
    private int access_time;
    @Value("${jwt.refresh_time}")
    private int refresh_time;
    @Value("${jwt.secret_key}")
    String secret_key;
    @Value("${jwt.claim}")
    String claim;

    @Override
    public String createToken(HttpServletRequest request, CustomUserPrincipal user, TokenType tokenType) {
        long lifeTime = getTokenLifeTime(tokenType);

        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String email = user.getUsername();
        Date date = new Date(System.currentTimeMillis() + lifeTime * 60 * 1000);

        return getToken(request, email, date, authorities);
    }

    @Override
    public String createToken(HttpServletRequest request, User user, TokenType tokenType) {
        long lifeTime = getTokenLifeTime(tokenType);

        List<String> authorities = Collections.singletonList(user.getRole().toString());
        String email = user.getEmail();
        Date date = new Date(System.currentTimeMillis() + lifeTime * 60 * 1000);

        return getToken(request, email, date, authorities);
    }

    @Override
    public String getToken(HttpServletRequest request, String subject, Date date, List<String> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(date)
                .withIssuer(request.getRequestURL().toString())
                .withClaim(claim, authorities)
                .sign(algorithm);
    }

    long getTokenLifeTime(TokenType tokenType) {
        if (tokenType.equals(TokenType.ACCESS)) return access_time;
        return refresh_time;
    }

    @Override
    public DecodedJWT validate(String refresh_token) {

        Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refresh_token);

        return decodedJWT;
    }

    @Override
    public void errorResponse(HttpServletResponse response, Exception e) throws IOException {
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", e.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
