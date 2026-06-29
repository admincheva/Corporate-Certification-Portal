package org.example.corporatecertificationportal.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CourseFilterDTO {
    private String title;
    private String provider;
    private String category;
    private Boolean refundable;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

}
