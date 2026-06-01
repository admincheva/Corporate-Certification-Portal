package org.example.corporatecertificationportal.controller;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.entity.CertificateSubmission;
import org.example.corporatecertificationportal.service.CertificateSubmissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
public class CertificateSubmissionController {

    private final CertificateSubmissionService service;

    @PostMapping
    public CertificateSubmission create(
            @RequestBody CertificateSubmission submission) {

        return service.create(submission);
    }

    @GetMapping
    public List<CertificateSubmission> getAll() {
        return service.getAll();
    }
}