package org.example.corporatecertificationportal.exception;

import org.springframework.http.HttpStatus;

public class CourseNotFoundException extends ApiException {
    public CourseNotFoundException() {

        super("Course not found", HttpStatus.NOT_FOUND);
    }
}
