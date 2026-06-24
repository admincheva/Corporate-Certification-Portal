package org.example.corporatecertificationportal.dto;

import lombok.*;
import org.example.corporatecertificationportal.enums.SubmissionStatus;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateSubmissionDTO {
    private Long id;
    private String username;
    private Long enrollmentId;
    private String courseTitle;
    private String certificateFileUrl;
    private String invoiceFileUrl;
    private String certificateNumber;
    private BigDecimal amountPaid;
    private SubmissionStatus status;
}
