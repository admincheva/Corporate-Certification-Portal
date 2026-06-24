package org.example.corporatecertificationportal.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.LoginDTO;
import org.example.corporatecertificationportal.dto.RegisterDTO;
import org.example.corporatecertificationportal.entity.User;
import org.example.corporatecertificationportal.enums.Role;
import org.example.corporatecertificationportal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(@Valid RegisterDTO request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "User already exists";
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.EMPLOYEE)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public User login(LoginDTO request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        boolean validPassword = passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!validPassword) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }
}
