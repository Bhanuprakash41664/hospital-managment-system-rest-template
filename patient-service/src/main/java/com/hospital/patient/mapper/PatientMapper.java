package com.hospital.patient.mapper;

import com.hospital.patient.dto.PatientRequest;
import com.hospital.patient.dto.PatientResponse;
import com.hospital.patient.entity.Patient;
import org.springframework.stereotype.Component;

/**
 * Converts between entities and DTOs so the service layer stays clean.
 * (Larger projects often generate this class with MapStruct.)
 */
@Component
public class PatientMapper {

    public Patient toEntity(PatientRequest request) {
        return Patient.builder()
                .name(request.name().trim())
                .email(normalizeEmail(request.email()))
                .dateOfBirth(request.dateOfBirth())
                .address(request.address())
                .build();
    }

    public void updateEntity(Patient patient, PatientRequest request) {
        patient.setName(request.name().trim());
        patient.setEmail(normalizeEmail(request.email()));
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setAddress(request.address());
    }

    public PatientResponse toResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getDateOfBirth(),
                patient.getAddress(),
                patient.getCreatedAt(),
                patient.getUpdatedAt());
    }

    // Emails are stored in lower case so "A@x.com" and "a@x.com" are treated as the same address
    public static String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }
}
