package org.example.corporatecertificationportal.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.example.corporatecertificationportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(@NotBlank(message = "Email is required")
                               @Email(message = "Invalid email format")
                               String email);
}
