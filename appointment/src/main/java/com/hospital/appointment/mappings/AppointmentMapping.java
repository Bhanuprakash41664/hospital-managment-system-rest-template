package com.hospital.appointment.mappings;

import com.hospital.appointment.dto.AppointmentRequest;
import com.hospital.appointment.dto.AppointmentResponse;
import com.hospital.appointment.dto.PatientResponse;
import com.hospital.appointment.entity.Appointment;
import com.hospital.appointment.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentMapping {
    public static Appointment toEntity(
            AppointmentRequest request,
            PatientResponse patientResponse) {

        Appointment appointment = new Appointment();

        appointment.setPatientId(patientResponse.id());
        appointment.setAppointmentDate(request.appointmentDate());
        appointment.setReason(request.reason());

        // Set default status if request status is null
        appointment.setStatus(
                request.status() != null
                        ? request.status()
                        : AppointmentStatus.SCHEDULED
        );

        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setUpdatedAt(LocalDateTime.now());

        return appointment;
    }

    public static AppointmentResponse toResponse(Appointment appointment) {

        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getAppointmentDate(),
                appointment.getReason(),
                appointment.getStatus(),
                appointment.getCreatedAt(),
                appointment.getUpdatedAt()
        );
    }

    public static void updateEntity(Appointment appointment, AppointmentRequest appointmentRequest, PatientResponse patientResponse) {
        appointment.setReason(appointmentRequest.reason());
        appointment.setAppointmentDate(appointmentRequest.appointmentDate());
        appointment.setStatus(appointmentRequest.status());
        appointment.setPatientId(patientResponse.id());
    }
}
