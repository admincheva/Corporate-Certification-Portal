package org.example.corporatecertificationportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String provider;

    private String externalUrl;

    private BigDecimal price;

    private String category;

    private boolean refundable;
}
