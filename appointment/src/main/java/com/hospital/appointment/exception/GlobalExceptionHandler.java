package com.hospital.appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException re)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(re.getMessage());
    }

    @ExceptionHandler(org.springframework.web.client.ResourceAccessException.class)
    public ResponseEntity<String> handleServiceNotFound(ResourceAccessException ex)
    {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<String> handlePatientNotFound(
            HttpClientErrorException.NotFound ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }


//    @ExceptionHandler(ConnectException.class)
//    public ResponseEntity<String> handleConnectionException(
//            ConnectException ex)
//    {
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                .body(ex.getMessage());
//    }



}
