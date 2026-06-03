package org.example.corporatecertificationportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private String username;
    private Long enrolledCourses;
    private Long completedCourses;
}