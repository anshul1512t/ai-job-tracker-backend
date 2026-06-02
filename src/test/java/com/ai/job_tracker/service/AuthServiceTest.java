package com.ai.job_tracker.service;

import com.ai.job_tracker.dto.request.LoginRequestDTO;
import com.ai.job_tracker.dto.request.RegisterRequestDTO;
import com.ai.job_tracker.dto.response.AuthResponseDTO;
import com.ai.job_tracker.enums.Role;
import com.ai.job_tracker.model.User;
import com.ai.job_tracker.repository.UserRepository;
import com.ai.job_tracker.security.jwt.JwtService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

        @Mock
        private UserRepository repo;

        @Mock
        private JwtService jwtService;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Mock
        private AuthenticationManager authenticationManager;

        @InjectMocks
        private AuthService authService;

        @Test
        void shouldRegisterUser() {
                RegisterRequestDTO request = new RegisterRequestDTO(
                                "Anshul",
                                "anshul@gmail.com",
                                "password");

                when(repo.findByEmail("anshul@gmail.com"))
                                .thenReturn(null);

                when(passwordEncoder.encode("password"))
                                .thenReturn("encodedPassword");

                when(jwtService.generateToken(any(User.class)))
                                .thenReturn("jwt-token");

                AuthResponseDTO response = authService.register(request);

                assertNotNull(response);
                assertEquals("jwt-token", response.getToken());
                assertEquals("Anshul", response.getName());
                assertEquals("anshul@gmail.com", response.getEmail());
                assertEquals("USER", response.getRole());

                verify(repo).save(any(User.class));
        }

        @Test
        void shouldThrowExceptionWhenEmailAlreadyExists() {
                RegisterRequestDTO request = new RegisterRequestDTO(
                                "Anshul",
                                "anshul@gmail.com",
                                "password");

                User existingUser = new User();
                existingUser.setEmail("anshul@gmail.com");

                when(repo.findByEmail("anshul@gmail.com"))
                                .thenReturn(existingUser);

                assertThrows(RuntimeException.class,
                                () -> authService.register(request));

                verify(repo, never()).save(any(User.class));
                verify(jwtService, never()).generateToken(any(User.class));
        }

        @Test
        void shouldLoginUser() {
                LoginRequestDTO request = new LoginRequestDTO(
                                "anshul@gmail.com",
                                "password");

                User user = new User();
                user.setName("Anshul");
                user.setEmail("anshul@gmail.com");
                user.setRole(Role.USER);

                when(repo.findByEmail("anshul@gmail.com"))
                                .thenReturn(user);

                when(jwtService.generateToken(user))
                                .thenReturn("jwt-token");

                AuthResponseDTO response = authService.login(request);

                assertNotNull(response);
                assertEquals("jwt-token", response.getToken());
                assertEquals("Anshul", response.getName());
                assertEquals("anshul@gmail.com", response.getEmail());
                assertEquals("USER", response.getRole());

                verify(authenticationManager).authenticate(any());
                verify(repo).findByEmail("anshul@gmail.com");
                verify(jwtService).generateToken(user);
        }

        @Test
        void shouldThrowExceptionForInvalidPassword() {
                LoginRequestDTO request = new LoginRequestDTO(
                                "anshul@gmail.com",
                                "wrongPassword");

                when(authenticationManager.authenticate(any()))
                                .thenThrow(new BadCredentialsException("Invalid"));

                assertThrows(RuntimeException.class,
                                () -> authService.login(request));

                verify(repo, never()).findByEmail(anyString());
                verify(jwtService, never()).generateToken(any(User.class));
        }
}