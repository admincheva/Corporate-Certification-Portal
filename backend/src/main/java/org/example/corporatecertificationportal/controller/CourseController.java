package org.example.corporatecertificationportal.controller;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.CourseDTO;
import org.example.corporatecertificationportal.dto.CourseFilterDTO;
import org.example.corporatecertificationportal.entity.Course;
import org.example.corporatecertificationportal.service.CourseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<CourseDTO> getAll() {
        return courseService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/{id}")
    public CourseDTO getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Course create(@RequestBody CourseDTO courseDTO) {
        return courseService.create(courseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Course update(@PathVariable Long id,
                         @RequestBody CourseDTO courseDTO) {
        return courseService.update(id, courseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }

    @GetMapping("/filter")
    public List<CourseDTO> filterCourses(CourseFilterDTO filter) {

        return courseService.filterCourses(filter);

    }

}