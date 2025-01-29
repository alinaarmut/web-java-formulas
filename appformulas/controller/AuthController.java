package org.example.appformulas.controller;


import org.example.appformulas.essence.request.AuthRequest;
import org.example.appformulas.essence.response.AuthResponse;
import org.example.appformulas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app/auth")
@CrossOrigin(origins = "http://localhost:4200")

public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
