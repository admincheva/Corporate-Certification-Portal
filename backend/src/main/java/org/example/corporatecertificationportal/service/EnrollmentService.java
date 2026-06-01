package org.example.corporatecertificationportal.service;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.repository.EnrollmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository repository;

    public Enrollment create(Enrollment enrollment) {
        return repository.save(enrollment);
    }

    public List<Enrollment> getAll() {
        return repository.findAll();
    }
}