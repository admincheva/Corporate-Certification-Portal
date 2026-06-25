package org.example.corporatecertificationportal.exception;

import org.springframework.http.HttpStatus;

public class CertificateNotFoundException extends ApiException {

    public CertificateNotFoundException() {
        super("Certificate not found.", HttpStatus.NOT_FOUND);
    }
}
