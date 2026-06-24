package org.example.corporatecertificationportal.service;

import org.example.corporatecertificationportal.dto.CertificateSubmissionDTO;
import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.entity.User;
import org.example.corporatecertificationportal.enums.SubmissionStatus;
import org.example.corporatecertificationportal.repository.EnrollmentRepository;
import org.example.corporatecertificationportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.entity.CertificateSubmission;
import org.example.corporatecertificationportal.repository.CertificateSubmissionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateSubmissionService {

    private final CertificateSubmissionRepository certificateSubmissionRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CertificateSubmission create(CertificateSubmissionDTO dto) {

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Enrollment enrollment = enrollmentRepository.findById(dto.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        CertificateSubmission submission = CertificateSubmission.builder()
                .certificateNumber(dto.getCertificateNumber())
                .status(SubmissionStatus.PENDING)
                .invoiceFileUrl(dto.getInvoiceFileUrl())
                .certificateFileUrl(dto.getCertificateFileUrl())
                .user(user)
                .enrollment(enrollment)
                .amountPaid(dto.getAmountPaid())
                .build();

        return certificateSubmissionRepository.save(submission);

    }

    public List<CertificateSubmissionDTO> getMySubmissions(String username){
        List<CertificateSubmission> submissions = certificateSubmissionRepository.findByUserUsername(username);
        return submissions.stream()
                .map(this::toDto)
                .toList();
    }

    public List<CertificateSubmission> getAll() {
        return certificateSubmissionRepository.findAll();
    }

    public CertificateSubmission getById(Long id) {
        return certificateSubmissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
    }

    public void rejectSubmission(Long id){
        CertificateSubmission submission = getById(id);
        submission.setStatus(SubmissionStatus.REJECTED);
        certificateSubmissionRepository.save(submission);
    }

    public void approveSubmission(Long id){
        CertificateSubmission submission = getById(id);
        submission.setStatus(SubmissionStatus.APPROVED);
        certificateSubmissionRepository.save(submission);
    }

    public void delete(Long id) {
        certificateSubmissionRepository.deleteById(id);
    }

    private CertificateSubmissionDTO toDto(CertificateSubmission certificateSubmission){
        return CertificateSubmissionDTO.builder()
                    .id(certificateSubmission.getId())
                    .username(certificateSubmission.getUser().getUsername())
                    .enrollmentId(certificateSubmission.getEnrollment().getId())
                    .courseTitle(certificateSubmission.getEnrollment().getCourse().getTitle())
                    .certificateFileUrl(certificateSubmission.getCertificateFileUrl())
                    .invoiceFileUrl(certificateSubmission.getInvoiceFileUrl())
                    .certificateNumber(certificateSubmission.getCertificateNumber())
                    .amountPaid(certificateSubmission.getAmountPaid())
                    .status(certificateSubmission.getStatus())
                    .build();
    }
}
