package org.example.corporatecertificationportal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.corporatecertificationportal.dto.EnrollmentDTO;
import org.example.corporatecertificationportal.entity.Course;
import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.entity.User;
import org.example.corporatecertificationportal.enums.EnrollmentStatus;
import org.example.corporatecertificationportal.exception.CourseNotFoundException;
import org.example.corporatecertificationportal.exception.EnrollmentAlreadyExistsException;
import org.example.corporatecertificationportal.exception.EnrollmentNotFoundException;
import org.example.corporatecertificationportal.exception.UserNotFoundException;
import org.example.corporatecertificationportal.repository.CourseRepository;
import org.example.corporatecertificationportal.repository.EnrollmentRepository;
import org.example.corporatecertificationportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<EnrollmentDTO> getMyLearning(String username) {
        log.info("Fetching enrollments for user: {}", username);
        return enrollmentRepository
                .findByUserUsername(username)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public EnrollmentDTO create(EnrollmentDTO enrollmentDTO, String username) {
        Long courseId = enrollmentDTO.getCourseId();

        if (courseId == null) {
            log.warn("Enrollment request from user '{}' is missing courseId", username);
            throw new IllegalArgumentException("courseId must not be null");
        }

        log.info("User '{}' requesting enrollment in course id={}", username, courseId);

        if (enrollmentRepository.existsByUserUsernameAndCourseId(username, courseId)) {
            log.warn("Duplicate enrollment attempt: user='{}', courseId={}", username, courseId);
            throw new EnrollmentAlreadyExistsException();
        }

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found: '{}'", username);
                    return new UserNotFoundException();
                });

        Course course = courseRepository
                .findById(courseId)
                .orElseThrow(() -> {
                    log.error("Course not found: id={}", courseId);
                    return new CourseNotFoundException();
                });

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .enrolledAt(LocalDate.now())
                .status(EnrollmentStatus.ENROLLED)
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);
        log.info("Enrollment created: user='{}', courseId={}, enrollmentId={}", username, courseId, saved.getId());
        return toDto(saved);
    }

    @Transactional
    public void complete(Long id) {
        log.info("Completing enrollment id={}", id);
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(EnrollmentNotFoundException::new);

        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void cancel(Long id) {
        log.info("Cancelling enrollment id={}", id);
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(EnrollmentNotFoundException::new);

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting enrollment id={}", id);
        enrollmentRepository.deleteById(id);
    }

    private EnrollmentDTO toDto(Enrollment enrollment) {
        return EnrollmentDTO.builder()
                .id(enrollment.getId())
                .username(enrollment.getUser().getUsername())
                .courseId(enrollment.getCourse().getId())
                .courseTitle(enrollment.getCourse().getTitle())
                .provider(enrollment.getCourse().getProvider())
                .enrolledAt(enrollment.getEnrolledAt())
                .status(enrollment.getStatus().name())
                .build();
    }
}
