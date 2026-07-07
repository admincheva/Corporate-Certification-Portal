package org.example.corporatecertificationportal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private Long id;
    private String username;

    @NotNull(message = "courseId must not be null")
    private Long courseId;

    private String courseTitle;
    private String provider;
    private LocalDate enrolledAt;
    private String status;
}