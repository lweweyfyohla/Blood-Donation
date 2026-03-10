package com.bloodbank.controller;

import com.bloodbank.config.JwtService;
import com.bloodbank.dto.request.LoginRequest;
import com.bloodbank.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User user = (User) auth.getPrincipal();
        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "name",  user.getName(),
                "role",  user.getRole().name()
        ));
    }
}
