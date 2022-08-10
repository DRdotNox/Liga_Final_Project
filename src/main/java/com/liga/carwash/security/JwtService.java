package com.liga.carwash.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.liga.carwash.enums.TokenType;
import com.liga.carwash.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface JwtService {
    String createToken(HttpServletRequest request, CustomUserPrincipal user, TokenType tokenType);

    String createToken(HttpServletRequest request, User user, TokenType tokenType);

    String getToken(HttpServletRequest request, String subject, Date date, List<String> authorities);

    void errorResponse(HttpServletResponse response, Exception e) throws IOException;

    DecodedJWT validate(String token);
}
