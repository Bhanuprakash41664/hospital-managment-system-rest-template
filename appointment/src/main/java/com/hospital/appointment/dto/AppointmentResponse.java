package com.hospital.appointment.dto;


import com.hospital.appointment.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long patientId,
        LocalDateTime appointmentDate,
        String reason,
        AppointmentStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}