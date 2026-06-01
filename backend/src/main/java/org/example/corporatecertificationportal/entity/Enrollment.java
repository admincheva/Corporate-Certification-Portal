package org.example.corporatecertificationportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.corporatecertificationportal.enums.EnrollmentStatus;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Course course;

    private LocalDate enrolledAt;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}
