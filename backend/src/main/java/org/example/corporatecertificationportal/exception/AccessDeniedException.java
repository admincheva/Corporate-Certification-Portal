package org.example.corporatecertificationportal.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ApiException {

    public AccessDeniedException() {
        super("You don't have permission to perform this operation.",
                HttpStatus.FORBIDDEN);
    }
}
