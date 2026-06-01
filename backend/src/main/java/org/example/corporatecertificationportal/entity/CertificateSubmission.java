package org.example.corporatecertificationportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.corporatecertificationportal.enums.SubmissionStatus;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cert-submission")
public class CertificateSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Enrollment enrollment;

    private String certificateFileUrl;

    private String invoiceFileUrl;

    private String certificateNumber;

    private BigDecimal amountPaid;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;
}
