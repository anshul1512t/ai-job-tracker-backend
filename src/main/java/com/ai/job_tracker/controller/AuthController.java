package com.ai.job_tracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.job_tracker.dto.request.LoginRequestDTO;
import com.ai.job_tracker.dto.request.RegisterRequestDTO;
import com.ai.job_tracker.dto.response.AuthResponseDTO;
import com.ai.job_tracker.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @PostMapping("register")
    public ResponseEntity<AuthResponseDTO> registration(@Valid @RequestBody RegisterRequestDTO registerDTO) {
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) { 
    
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @GetMapping("me")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getName() + SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
    }
    
    @GetMapping("test")
    public ResponseEntity<String> greet(){
        return ResponseEntity.ok("Hello World");
    }
    
    
}
