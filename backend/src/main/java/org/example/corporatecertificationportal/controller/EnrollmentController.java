package org.example.corporatecertificationportal.controller;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService service;

    @PostMapping
    public Enrollment create(@RequestBody Enrollment enrollment) {
        return service.create(enrollment);
    }

    @GetMapping
    public List<Enrollment> getAll() {
        return service.getAll();
    }
}