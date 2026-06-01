package org.example.corporatecertificationportal.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.entity.Course;
import org.example.corporatecertificationportal.repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;

    public Course create(Course course) {
        return repository.save(course);
    }

    public List<Course> getAll() {
        return repository.findAll();
    }
}
