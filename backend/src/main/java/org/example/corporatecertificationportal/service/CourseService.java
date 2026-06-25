package org.example.corporatecertificationportal.service;

import org.example.corporatecertificationportal.dto.CourseDTO;
import org.example.corporatecertificationportal.exception.CourseNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.entity.Course;
import org.example.corporatecertificationportal.repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;

    public Course create(CourseDTO courseDTO) {

        Course course = Course.builder()
                .title(courseDTO.getTitle())
                .price(courseDTO.getPrice())
                .category(courseDTO.getCategory())
                .externalUrl(courseDTO.getExternalUrl())
                .provider(courseDTO.getProvider())
                .refundable(courseDTO.isRefundable())
                .build();

        return repository.save(course);
    }

    public Course getById(Long id){
        return repository.findById(id).orElseThrow(CourseNotFoundException::new);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Course update(Long id, CourseDTO courseDTO){
        Course course = getById(id);
        course = Course.builder()
                .id(course.getId())
                .title(courseDTO.getTitle())
                .price(courseDTO.getPrice())
                .category(courseDTO.getCategory())
                .externalUrl(courseDTO.getExternalUrl())
                .provider(courseDTO.getProvider())
                .refundable(courseDTO.isRefundable())
                .build();

        return repository.save(course);
    }

    public List<Course> getAll() {
        return repository.findAll();
    }
}
