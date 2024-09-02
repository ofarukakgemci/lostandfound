package com.ofa.lostandfound.controller;

import com.ofa.lostandfound.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(JwtAuthenticationToken authentication) {

        return ResponseEntity.ok(Map.of(
                "userId", authentication.getName(),
                "userName", userService.getUserName(authentication.getName()),
                "authorities", authentication.getAuthorities(),
                "exp", authentication.getToken().getExpiresAt().toEpochMilli()
        ));
    }
}