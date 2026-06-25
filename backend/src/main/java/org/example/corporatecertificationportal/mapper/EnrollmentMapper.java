package org.example.corporatecertificationportal.mapper;

import org.example.corporatecertificationportal.dto.EnrollmentDTO;
import org.example.corporatecertificationportal.entity.Enrollment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    Enrollment toEntity(EnrollmentDTO dto);
    EnrollmentDTO toDTO(Enrollment enrollment);
    List<EnrollmentDTO> toDTOList(List<Enrollment> enrollments);
}
