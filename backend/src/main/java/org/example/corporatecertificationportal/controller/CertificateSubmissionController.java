package org.example.corporatecertificationportal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.CertificateSubmissionDTO;
import org.example.corporatecertificationportal.entity.CertificateSubmission;
import org.example.corporatecertificationportal.service.CertificateSubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CertificateSubmissionController {

    private final CertificateSubmissionService submissionService;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping
    public ResponseEntity<CertificateSubmissionDTO> create(@Valid @RequestBody CertificateSubmissionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(submissionService.create(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{username}")
    public List<CertificateSubmissionDTO> getMySubmissions(@PathVariable String username) {
        return submissionService.getMySubmissions(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<CertificateSubmissionDTO> getAll() {
        return submissionService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/id/{id}")
    public CertificateSubmission getById(@PathVariable Long id) {
        return submissionService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public void approveSubmission(@PathVariable Long id) {
        submissionService.approveSubmission(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reject")
    public void rejectSubmission(@PathVariable Long id) {
        submissionService.rejectSubmission(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        submissionService.delete(id);
    }

}