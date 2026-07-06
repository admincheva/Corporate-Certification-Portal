package org.example.corporatecertificationportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentUserDTO {

    private Long id;
    private String username;
    private String email;
    private String role;

}