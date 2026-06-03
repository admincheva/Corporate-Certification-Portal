package org.example.corporatecertificationportal.repository;

import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Long countByUserUsername(String username);

    Long countByUserUsernameAndStatus(String username, EnrollmentStatus status);
}
