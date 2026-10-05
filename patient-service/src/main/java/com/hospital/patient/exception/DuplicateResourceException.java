package com.hospital.patient.exception;

/**
 * Thrown when a resource already exists, e.g. a duplicate email (mapped to HTTP 409).
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
