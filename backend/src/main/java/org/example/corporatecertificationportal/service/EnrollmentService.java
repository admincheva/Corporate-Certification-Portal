package org.example.corporatecertificationportal.service;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.EnrollmentDTO;
import org.example.corporatecertificationportal.entity.Course;
import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.entity.User;
import org.example.corporatecertificationportal.enums.EnrollmentStatus;
import org.example.corporatecertificationportal.exception.CourseNotFoundException;
import org.example.corporatecertificationportal.exception.EnrollmentNotFoundException;
import org.example.corporatecertificationportal.exception.UserNotFoundException;
import org.example.corporatecertificationportal.mapper.EnrollmentMapper;
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
    private final EnrollmentMapper enrollmentMapper;

    public List<EnrollmentDTO> getMyLearning(String username) {
        return enrollmentMapper
                .toDTOList(enrollmentRepository.findByUserUsername(username));
    }

    public Enrollment create(EnrollmentDTO enrollmentDTO){
        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentDTO);
        return enrollmentRepository.save(enrollment);
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
}