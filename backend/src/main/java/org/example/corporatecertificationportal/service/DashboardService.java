package org.example.corporatecertificationportal.service;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.DashboardDTO;
import org.example.corporatecertificationportal.enums.EnrollmentStatus;
import org.example.corporatecertificationportal.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EnrollmentRepository enrollmentRepository;

    public DashboardDTO getDashboard(
            String username
    ) {

        DashboardDTO dto = new DashboardDTO();

        dto.setUsername(username);
        dto.setEnrolledCourses(enrollmentRepository.countByUserUsername(username));
        dto.setCompletedCourses(
                enrollmentRepository
                        .countByUserUsernameAndStatus(username, EnrollmentStatus.COMPLETED));

        return dto;
    }
}
