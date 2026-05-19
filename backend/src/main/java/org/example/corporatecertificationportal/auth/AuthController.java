package org.example.corporatecertificationportal.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.LoginDTO;
import org.example.corporatecertificationportal.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid
            @RequestBody
            com.example.portal.dto.RegisterDTO request
    ) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid
            @RequestBody
            LoginDTO request
    ) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}