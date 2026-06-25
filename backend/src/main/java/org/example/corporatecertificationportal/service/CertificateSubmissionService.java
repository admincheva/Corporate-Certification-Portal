package org.example.corporatecertificationportal.service;

import org.example.corporatecertificationportal.dto.CertificateSubmissionDTO;
import org.example.corporatecertificationportal.enums.SubmissionStatus;
import org.example.corporatecertificationportal.exception.CertificateNotFoundException;
import org.example.corporatecertificationportal.mapper.CertificateSubmissionMapper;
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
    private final CertificateSubmissionMapper certificateSubmissionMapper;

    public CertificateSubmission create(CertificateSubmissionDTO dto) {

        CertificateSubmission submission = certificateSubmissionMapper.toEntity(dto);
        return certificateSubmissionRepository.save(submission);

    }

    public List<CertificateSubmissionDTO> getMySubmissions(String username){
        return certificateSubmissionMapper
                .toDTOList(certificateSubmissionRepository
                        .findByUserUsername(username));
    }

    public List<CertificateSubmissionDTO> getAll() {

        return certificateSubmissionMapper
                .toDTOList(certificateSubmissionRepository.findAll());
    }

    public CertificateSubmission getById(Long id) {
        return certificateSubmissionRepository.findById(id)
                .orElseThrow(CertificateNotFoundException::new);
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
}
