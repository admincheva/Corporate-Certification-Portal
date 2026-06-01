package org.example.corporatecertificationportal.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.LoginDTO;
import org.example.corporatecertificationportal.dto.RegisterDTO;
import org.example.corporatecertificationportal.entity.User;
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
                .password(
                        passwordEncoder.encode(request.getPassword())
                )
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(LoginDTO request) {

        Optional<User> optionalUser =
                userRepository.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return "Invalid username or password";
        }

        User user = optionalUser.get();

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!matches) {
            return "Invalid username or password";
        }

        return "Login successful";
    }
}
