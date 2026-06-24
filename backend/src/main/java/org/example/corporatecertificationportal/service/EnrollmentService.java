package org.example.corporatecertificationportal.service;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.EnrollmentDTO;
import org.example.corporatecertificationportal.entity.Course;
import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.entity.User;
import org.example.corporatecertificationportal.enums.EnrollmentStatus;
import org.example.corporatecertificationportal.repository.CourseRepository;
import org.example.corporatecertificationportal.repository.EnrollmentRepository;
import org.example.corporatecertificationportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public List<EnrollmentDTO> getMyLearning(String username) {
        return enrollmentRepository.findByUserUsername(username)
                .stream()
                .map(enrollment -> EnrollmentDTO.builder()
                                .courseTitle(enrollment.getCourse().getTitle())
                                .provider(enrollment.getCourse().getProvider())
                                .enrolledAt(enrollment.getEnrolledAt())
                                .status(enrollment.getStatus().name())
                                .build())
                .toList();
    }

    public Enrollment create(EnrollmentDTO enrollmentDTO){
        User user = userRepository.findByUsername(enrollmentDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .enrolledAt(LocalDate.now())
                .status(EnrollmentStatus.ENROLLED)
                .build();

        return enrollmentRepository.save(enrollment);
    }

    public void complete(Long id){
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment course not found"));

        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        enrollmentRepository.save(enrollment);
    }

    public void cancel(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment course not found"));

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.save(enrollment);

    }

    public void delete(Long id){
        enrollmentRepository.deleteById(id);
    }
}