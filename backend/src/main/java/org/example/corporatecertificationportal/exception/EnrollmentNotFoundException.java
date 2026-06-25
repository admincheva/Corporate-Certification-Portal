package org.example.corporatecertificationportal.exception;

import org.springframework.http.HttpStatus;

public class EnrollmentNotFoundException extends ApiException {
    public EnrollmentNotFoundException() {

        super("Enrollment not found", HttpStatus.NOT_FOUND);
    }
}
