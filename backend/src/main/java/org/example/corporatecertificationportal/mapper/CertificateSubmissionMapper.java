package org.example.corporatecertificationportal.mapper;

import org.example.corporatecertificationportal.dto.CertificateSubmissionDTO;
import org.example.corporatecertificationportal.entity.CertificateSubmission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificateSubmissionMapper {

    CertificateSubmission toEntity(CertificateSubmissionDTO dto);
    CertificateSubmissionDTO toDTO(CertificateSubmission submission);
    List<CertificateSubmissionDTO> toDTOList(List<CertificateSubmission> submissions);
}
