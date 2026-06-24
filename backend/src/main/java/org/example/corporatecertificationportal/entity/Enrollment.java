package org.example.corporatecertificationportal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.corporatecertificationportal.enums.EnrollmentStatus;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@Entity
@Table(name = "enrollments")
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private LocalDate enrolledAt;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}
