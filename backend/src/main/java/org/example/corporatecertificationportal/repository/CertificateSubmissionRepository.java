package org.example.corporatecertificationportal.repository;

import org.example.corporatecertificationportal.entity.CertificateSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateSubmissionRepository extends JpaRepository<CertificateSubmission, Long> {
}
