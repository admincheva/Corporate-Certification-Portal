package org.example.corporatecertificationportal.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.CurrentUserDTO;
import org.example.corporatecertificationportal.dto.RegisterDTO;
import org.example.corporatecertificationportal.entity.User;
import org.example.corporatecertificationportal.repository.UserRepository;
import org.example.corporatecertificationportal.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(
        origins = "http://localhost:4200",
        allowCredentials = "true"
)
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @Valid @RequestBody RegisterDTO request
    ) {

        return ResponseEntity.ok(
                authService.register(request)
        );

    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserDTO> me(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository
                .findByUsername(authentication.getName())
                .orElseThrow();

        return ResponseEntity.ok(
                new CurrentUserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole().name()
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        request.getSession().invalidate();
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok().build();
    }

}