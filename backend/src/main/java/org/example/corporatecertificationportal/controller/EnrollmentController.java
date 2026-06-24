package org.example.corporatecertificationportal.controller;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.EnrollmentDTO;
import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.service.EnrollmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{username}")
    public List<EnrollmentDTO> getMyLearning(@PathVariable String username) {
        return enrollmentService.getMyLearning(username);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping
    public Enrollment create(@RequestBody EnrollmentDTO enrollmentDTO) {
        return enrollmentService.create(enrollmentDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PutMapping("/{id}/complete")
    public void complete(@PathVariable Long id) {
        enrollmentService.complete(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        enrollmentService.cancel(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        enrollmentService.delete(id);
    }

}