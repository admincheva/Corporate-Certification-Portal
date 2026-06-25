package org.example.corporatecertificationportal.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String provider;
    private String category;
    private BigDecimal price;
    private boolean refundable;
    private String externalUrl;
    private String status;
}
