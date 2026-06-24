package org.example.corporatecertificationportal.controller;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.EnrollmentDTO;
import org.example.corporatecertificationportal.entity.Enrollment;
import org.example.corporatecertificationportal.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/{username}")
    public List<EnrollmentDTO> getMyLearning(@PathVariable String username) {
        return enrollmentService.getMyLearning(username);
    }

    @PostMapping
    public Enrollment create(@RequestBody EnrollmentDTO enrollmentDTO) {
        return enrollmentService.create(enrollmentDTO);
    }

    @PutMapping("/{id}/complete")
    public void complete(@PathVariable Long id) {
        enrollmentService.complete(id);
    }

    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        enrollmentService.cancel(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        enrollmentService.delete(id);
    }

}