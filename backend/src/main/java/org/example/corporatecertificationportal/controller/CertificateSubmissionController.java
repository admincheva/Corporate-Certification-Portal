package org.example.corporatecertificationportal.controller;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.CertificateSubmissionDTO;
import org.example.corporatecertificationportal.entity.CertificateSubmission;
import org.example.corporatecertificationportal.service.CertificateSubmissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CertificateSubmissionController {

    private final CertificateSubmissionService submissionService;

    @PostMapping
    public CertificateSubmission create(@RequestBody CertificateSubmissionDTO dto) {
        return submissionService.create(dto);
    }

    @GetMapping("/{username}")
    public List<CertificateSubmissionDTO> getMySubmissions(@PathVariable String username) {
        return submissionService.getMySubmissions(username);
    }

    @GetMapping
    public List<CertificateSubmission> getAll() {
        return submissionService.getAll();
    }

    @GetMapping("/id/{id}")
    public CertificateSubmission getById(@PathVariable Long id) {
        return submissionService.getById(id);
    }

    @PutMapping("/{id}/approve")
    public void approveSubmission(@PathVariable Long id) {
        submissionService.approveSubmission(id);
    }

    @PutMapping("/{id}/reject")
    public void rejectSubmission(@PathVariable Long id) {
        submissionService.rejectSubmission(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        submissionService.delete(id);
    }

}