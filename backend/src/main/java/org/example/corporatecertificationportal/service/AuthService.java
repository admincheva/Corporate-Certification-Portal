package org.example.corporatecertificationportal.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.RegisterDTO;
import org.example.corporatecertificationportal.entity.User;
import org.example.corporatecertificationportal.enums.Role;
import org.example.corporatecertificationportal.exception.UserAlreadyExistsException;
import org.example.corporatecertificationportal.mapper.UserMapper;
import org.example.corporatecertificationportal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User register(@Valid RegisterDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = userMapper.toEntity(request);
        user.setRole(Role.EMPLOYEE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}