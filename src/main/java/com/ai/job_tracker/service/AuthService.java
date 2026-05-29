package com.ai.job_tracker.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ai.job_tracker.dto.request.LoginRequestDTO;
import com.ai.job_tracker.dto.request.RegisterRequestDTO;
import com.ai.job_tracker.dto.response.AuthResponseDTO;
import com.ai.job_tracker.enums.Role;

import com.ai.job_tracker.model.User;
import com.ai.job_tracker.repository.UserRepository;
import com.ai.job_tracker.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository repo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO requestDTO) {
        if (repo.findByEmail(requestDTO.getEmail()) != null) {
            throw new RuntimeException("User with email " + requestDTO.getEmail() + " already exists");
        }

        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        repo.save(user);

        return new AuthResponseDTO(jwtService.generateToken(user), user.getName(), user.getEmail(),
                user.getRole().name());
    }

    public AuthResponseDTO login(LoginRequestDTO loginDTO) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()));

        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Invalid email || password from AuthService");
        }

        User user = repo.findByEmail(loginDTO.getEmail());

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(
                token,
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }

}
