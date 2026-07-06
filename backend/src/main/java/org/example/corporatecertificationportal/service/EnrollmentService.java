package org.example.corporatecertificationportal.service;

import lombok.RequiredArgsConstructor;
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

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public List<EnrollmentDTO> getMyLearning(String username) {
        return enrollmentRepository
                .findByUserUsername(username)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public EnrollmentDTO create(EnrollmentDTO enrollmentDTO, String username) {
        if (enrollmentDTO.getCourseId() == null) {
            throw new CourseNotFoundException();
        }

        if (enrollmentRepository.existsByUserUsernameAndCourseId(username, enrollmentDTO.getCourseId())) {
            throw new EnrollmentAlreadyExistsException();
        }

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        Course course = courseRepository
                .findById(enrollmentDTO.getCourseId())
                .orElseThrow(CourseNotFoundException::new);

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .enrolledAt(LocalDate.now())
                .status(EnrollmentStatus.ENROLLED)
                .build();

        return toDto(enrollmentRepository.save(enrollment));
    }

    public void complete(Long id){
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(EnrollmentNotFoundException::new);

        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        enrollmentRepository.save(enrollment);
    }

    public void cancel(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(EnrollmentNotFoundException::new);

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.save(enrollment);

    }

    public void delete(Long id){
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
