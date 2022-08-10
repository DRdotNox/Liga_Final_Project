package com.liga.carwash.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liga.carwash.enums.TokenType;
import com.liga.carwash.model.DTO.UserRegistrationDTO;
import com.liga.carwash.model.DTO.UserShortNameDTO;
import com.liga.carwash.model.User;
import com.liga.carwash.security.JwtService;
import com.liga.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/auth")
@RestController
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());

                String email = jwtService.validate(refresh_token).getSubject();

                User user = userService.getUserByEmail(email);
                String access_token = jwtService.createToken(request, user, TokenType.ACCESS);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                log.error("Error login in " + e.getMessage());
                jwtService.errorResponse(response, e);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @PostMapping("/registration")
    @PreAuthorize("hasRole('ANONYMOUS')")
    public String registerUser(@Validated @RequestBody UserRegistrationDTO userRegistrationDTO) {
        return userService.registerUser(userRegistrationDTO);
    }

    @PostMapping("/restore-account")
    public String restoreAccount(@Validated @RequestBody UserShortNameDTO userShortNameDTO) {
        return userService.restoreAccount(userShortNameDTO);
    }
}
