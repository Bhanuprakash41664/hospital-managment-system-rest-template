package com.hospital.appointment.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Output DTO: the data the API returns to clients.
 */
public record PatientResponse(
        Long id,
        String name,
        String email,
        LocalDate dateOfBirth,
        String address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
