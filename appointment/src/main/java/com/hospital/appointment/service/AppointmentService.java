package com.hospital.appointment.service;


import com.hospital.appointment.dto.AppointmentRequest;
import com.hospital.appointment.dto.AppointmentResponse;
import com.hospital.appointment.dto.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    AppointmentResponse createAppointment( AppointmentRequest appointmentRequest);


    AppointmentResponse getAppointmentById(Long id);

    PageResponse<AppointmentResponse> getAllAppointments(Pageable pageable);

    AppointmentResponse updateAppointment(Long id, @Valid AppointmentRequest appointmentRequest);

    void deleteAppointmentById(Long id);
}


