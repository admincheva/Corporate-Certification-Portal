package org.example.corporatecertificationportal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.corporatecertificationportal.enums.SubmissionStatus;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@Entity
@Table(name = "cert-submission")
@NoArgsConstructor
@AllArgsConstructor
public class CertificateSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "enrollment_id",
            nullable = false,
            unique = true
    )
    private Enrollment enrollment;

    @Column(nullable = false)
    private String certificateFileUrl;

    @Column(nullable = true)
    private String invoiceFileUrl;

    @Column(nullable = false)
    private String certificateName;

    @Column(nullable = false)
    private String issuingOrganization;

    @Column(nullable = false)
    private String issueDate;

    @Column(nullable = true)
    private String certificateUrl;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = true)
    private String certificateNumber;

    @Column(nullable = true)
    private BigDecimal amountPaid;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;
}
