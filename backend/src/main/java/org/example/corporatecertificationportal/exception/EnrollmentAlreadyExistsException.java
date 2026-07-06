package org.example.corporatecertificationportal.exception;

import org.springframework.http.HttpStatus;

public class EnrollmentAlreadyExistsException extends ApiException {

    public EnrollmentAlreadyExistsException() {
        super("You are already enrolled.", HttpStatus.CONFLICT);
    }
}
