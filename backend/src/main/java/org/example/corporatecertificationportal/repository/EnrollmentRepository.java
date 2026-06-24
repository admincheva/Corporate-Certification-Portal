package org.example.corporatecertificationportal.repository;

import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Long countByUserUsername(String username);
    Long countByUserUsernameAndStatus(String username, EnrollmentStatus status);
    List<Enrollment> findByUserUsername(String username);
}
