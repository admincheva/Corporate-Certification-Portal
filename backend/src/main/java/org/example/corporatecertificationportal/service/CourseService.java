package org.example.corporatecertificationportal.service;

import org.example.corporatecertificationportal.dto.CourseDTO;
import org.example.corporatecertificationportal.dto.CourseFilterDTO;
import org.example.corporatecertificationportal.exception.CourseNotFoundException;
import org.example.corporatecertificationportal.mapper.CourseMapper;
import org.example.corporatecertificationportal.specification.CourseSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.entity.Course;
import org.example.corporatecertificationportal.repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;
    private final CourseMapper courseMapper;

    public Course create(CourseDTO courseDTO) {

        Course course = courseMapper.toEntity(courseDTO);
        return repository.save(course);
    }

    public CourseDTO getById(Long id){
        Course course = repository.findById(id)
                .orElseThrow(CourseNotFoundException::new);

        return courseMapper.toDTO(course);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Course update(Long id, CourseDTO courseDTO){
        Course course = repository.findById(id)
                .orElseThrow(CourseNotFoundException::new);

        course.setTitle(courseDTO.getTitle());
        course.setPrice(courseDTO.getPrice());
        course.setCategory(courseDTO.getCategory());
        course.setExternalUrl(courseDTO.getExternalUrl());
        course.setProvider(courseDTO.getProvider());
        course.setRefundable(courseDTO.isRefundable());

        return repository.save(course);
    }

    public List<CourseDTO> getAll() {
        return courseMapper.toDTOList(repository.findAll());
    }

    public List<CourseDTO> filterCourses(CourseFilterDTO filter) {

        Specification<Course> specification = Specification
                .where(CourseSpecification.hasTitle(filter.getTitle()))
                .and(CourseSpecification.hasProvider(filter.getProvider()))
                .and(CourseSpecification.hasCategory(filter.getCategory()))
                .and(CourseSpecification.isRefundable(filter.getRefundable()))
                .and(CourseSpecification.hasMinPrice(filter.getMinPrice()))
                .and(CourseSpecification.hasMaxPrice(filter.getMaxPrice()));

        return repository.findAll(specification)
                .stream()
                .map(courseMapper::toDTO)
                .toList();
    }
}
