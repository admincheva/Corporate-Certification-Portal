package org.example.corporatecertificationportal.mapper;

import org.example.corporatecertificationportal.dto.CertificateSubmissionDTO;
import org.example.corporatecertificationportal.entity.CertificateSubmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificateSubmissionMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "enrollment.id", target = "enrollmentId")
    @Mapping(source = "enrollment.course.title", target = "courseTitle")
    CertificateSubmissionDTO toDTO(CertificateSubmission submission);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "enrollment", ignore = true)
    CertificateSubmission toEntity(CertificateSubmissionDTO dto);

    List<CertificateSubmissionDTO> toDTOList(List<CertificateSubmission> submissions);
}
