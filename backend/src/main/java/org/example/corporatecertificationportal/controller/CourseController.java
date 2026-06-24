package org.example.corporatecertificationportal.controller;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.CourseDTO;
import org.example.corporatecertificationportal.entity.Course;
import org.example.corporatecertificationportal.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<Course> getAll() {
        return courseService.getAll();
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @PostMapping
    public Course create(@RequestBody CourseDTO courseDTO) {
        return courseService.create(courseDTO);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id,
                         @RequestBody CourseDTO courseDTO) {
        return courseService.update(id, courseDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }

}