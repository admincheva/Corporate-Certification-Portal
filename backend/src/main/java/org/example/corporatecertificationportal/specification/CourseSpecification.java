package org.example.corporatecertificationportal.specification;

import org.example.corporatecertificationportal.entity.Course;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class CourseSpecification {

    public static Specification<Course> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Course> hasCategory(String category) {

        return (root, query, criteriaBuilder) ->{
            if (category == null || category.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("category")),
                    "%" + category.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Course> hasProvider(String provider) {

        return (root, query, criteriaBuilder) -> {

            if (provider == null || provider.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("provider")),
                    "%" + provider.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Course> isRefundable(Boolean refundable) {

        return (root, query, criteriaBuilder) -> {
            if (refundable == null) {
                return criteriaBuilder.conjunction();
            }

           return criteriaBuilder.equal(
                    root.get("refundable"),
                    refundable
            );
        };

    }

    public static Specification<Course> hasMinPrice(BigDecimal price) {

        return (root, query, criteriaBuilder) -> {

            if (price == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get("price"),
                    price
            );
        };

    }

    public static Specification<Course> hasMaxPrice(BigDecimal price){
        return (root, query, criteriaBuilder) -> {

            if (price == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get("price"),
                    price
            );
        };

    }

}
