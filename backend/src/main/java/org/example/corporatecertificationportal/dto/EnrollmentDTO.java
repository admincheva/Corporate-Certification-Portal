package org.example.corporatecertificationportal.dto;

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
    private Long courseId;
    private String courseTitle;
    private String provider;
    private LocalDate enrolledAt;
    private String status;
}