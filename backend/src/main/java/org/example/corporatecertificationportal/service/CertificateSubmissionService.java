package org.example.corporatecertificationportal.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.entity.CertificateSubmission;
import org.example.corporatecertificationportal.repository.CertificateSubmissionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateSubmissionService {

    private final CertificateSubmissionRepository repository;

    public CertificateSubmission create(CertificateSubmission submission) {
        return repository.save(submission);
    }

    public List<CertificateSubmission> getAll() {
        return repository.findAll();
    }

    public CertificateSubmission getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
