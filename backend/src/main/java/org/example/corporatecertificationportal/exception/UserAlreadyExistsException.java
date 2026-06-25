package org.example.corporatecertificationportal.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {

    public UserAlreadyExistsException() {
        super("User already exists.", HttpStatus.CONFLICT);
    }
}
