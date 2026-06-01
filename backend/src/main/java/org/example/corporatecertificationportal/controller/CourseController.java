package org.example.corporatecertificationportal.controller;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.entity.Course;
import org.example.corporatecertificationportal.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @PostMapping
    public Course create(@RequestBody Course course) {
        return service.create(course);
    }

    @GetMapping
    public List<Course> getAll() {
        return service.getAll();
    }
}