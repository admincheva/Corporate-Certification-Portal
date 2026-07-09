package org.example.corporatecertificationportal.service;

import org.example.corporatecertificationportal.dto.CertificateSubmissionDTO;
import org.example.corporatecertificationportal.entity.CertificateSubmission;
import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.entity.User;
import org.example.corporatecertificationportal.enums.SubmissionStatus;
import org.example.corporatecertificationportal.exception.CertificateNotFoundException;
import org.example.corporatecertificationportal.exception.EnrollmentNotFoundException;
import org.example.corporatecertificationportal.exception.UserNotFoundException;
import org.example.corporatecertificationportal.mapper.CertificateSubmissionMapper;
import org.example.corporatecertificationportal.repository.CertificateSubmissionRepository;
import org.example.corporatecertificationportal.repository.EnrollmentRepository;
import org.example.corporatecertificationportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificateSubmissionService {

    private final CertificateSubmissionRepository certificateSubmissionRepository;
    private final CertificateSubmissionMapper certificateSubmissionMapper;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public CertificateSubmissionDTO create(CertificateSubmissionDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(UserNotFoundException::new);

        Enrollment enrollment = enrollmentRepository.findById(dto.getEnrollmentId())
                .orElseThrow(EnrollmentNotFoundException::new);

        CertificateSubmission submission = CertificateSubmission.builder()
                .user(user)
                .enrollment(enrollment)
                .certificateFileUrl(dto.getCertificateFileUrl())
                .invoiceFileUrl(dto.getInvoiceFileUrl())
                .certificateNumber(dto.getCertificateNumber())
                .amountPaid(dto.getAmountPaid())
                .status(SubmissionStatus.PENDING)
                .build();

        return certificateSubmissionMapper.toDTO(certificateSubmissionRepository.save(submission));
    }

    public CertificateSubmissionDTO createWithFiles(
            String username,
            Long enrollmentId,
            String certificateNumber,
            BigDecimal amountPaid,
            MultipartFile certificateFile,
            MultipartFile invoiceFile) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(EnrollmentNotFoundException::new);

        String certUrl = saveFile(certificateFile, "certificates");
        String invoiceUrl = (invoiceFile != null && !invoiceFile.isEmpty())
                ? saveFile(invoiceFile, "invoices")
                : null;

        CertificateSubmission submission = CertificateSubmission.builder()
                .user(user)
                .enrollment(enrollment)
                .certificateFileUrl(certUrl)
                .invoiceFileUrl(invoiceUrl)
                .certificateNumber(certificateNumber)
                .amountPaid(amountPaid)
                .status(SubmissionStatus.PENDING)
                .build();

        return certificateSubmissionMapper.toDTO(certificateSubmissionRepository.save(submission));
    }

    private String saveFile(MultipartFile file, String subfolder) {
        try {
            Path dir = Paths.get(uploadDir, subfolder);
            Files.createDirectories(dir);
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = dir.resolve(filename);
            file.transferTo(target.toFile());
            return "/uploads/" + subfolder + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }

    public List<CertificateSubmissionDTO> getMySubmissions(String username) {
        return certificateSubmissionMapper
                .toDTOList(certificateSubmissionRepository.findByUserUsername(username));
    }

    public List<CertificateSubmissionDTO> getAll() {
        return certificateSubmissionMapper
                .toDTOList(certificateSubmissionRepository.findAll());
    }

    public CertificateSubmission getById(Long id) {
        return certificateSubmissionRepository.findById(id)
                .orElseThrow(CertificateNotFoundException::new);
    }

    public void rejectSubmission(Long id) {
        CertificateSubmission submission = getById(id);
        submission.setStatus(SubmissionStatus.REJECTED);
        certificateSubmissionRepository.save(submission);
    }

    public void approveSubmission(Long id) {
        CertificateSubmission submission = getById(id);
        submission.setStatus(SubmissionStatus.APPROVED);
        certificateSubmissionRepository.save(submission);
    }

    public void delete(Long id) {
        certificateSubmissionRepository.deleteById(id);
    }
}
