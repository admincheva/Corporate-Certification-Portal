package org.example.corporatecertificationportal.mapper;

import org.example.corporatecertificationportal.dto.CourseDTO;
import org.example.corporatecertificationportal.entity.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toEntity(CourseDTO dto);
    CourseDTO toDTO(Course course);
    List<CourseDTO> toDTOList(List<Course> courses);
}
