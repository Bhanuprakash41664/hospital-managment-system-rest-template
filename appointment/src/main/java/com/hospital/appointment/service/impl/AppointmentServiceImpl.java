package com.hospital.appointment.service.impl;

import com.hospital.appointment.client.PatientClient;
import com.hospital.appointment.dto.AppointmentRequest;
import com.hospital.appointment.dto.AppointmentResponse;
import com.hospital.appointment.dto.PageResponse;
import com.hospital.appointment.dto.PatientResponse;
import com.hospital.appointment.entity.Appointment;
import com.hospital.appointment.exception.ResourceNotFoundException;
import com.hospital.appointment.mappings.AppointmentMapping;
import com.hospital.appointment.repository.AppointmentRepository;
import com.hospital.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final RestTemplate restTemplate;
    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;

    @Value("${patient.service.url}")
    private String patientServiceUrl;

    @Override
    public AppointmentResponse createAppointment(
            AppointmentRequest appointmentRequest) {

        log.info("Create Appointment method called");

        long patientId = appointmentRequest.patientId();

        log.info("Patient ID: {}", patientId);


        PatientResponse patientResponse = patientClient.getPatientById(patientId);

        Appointment appointment =
                AppointmentMapping.toEntity(
                        appointmentRequest,
                        patientResponse
                );

        Appointment savedAppointment =
                appointmentRepository.save(appointment);

        return AppointmentMapping.toResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {


        return AppointmentMapping.toResponse(findAppointment(id));
    }

    @Override
    public PageResponse<AppointmentResponse> getAllAppointments(Pageable pageable) {
        return PageResponse.from(appointmentRepository.findAll(pageable).map(AppointmentMapping::toResponse));

    }

    @Override
    @Transactional
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest appointmentRequest) {

        Appointment appointment = findAppointment(id);
        PatientResponse patientResponse = patientClient.getPatientById(appointmentRequest.patientId());
        AppointmentMapping.updateEntity(appointment,appointmentRequest,patientResponse);
        return AppointmentMapping.toResponse(appointment);
    }

    @Override
    public void deleteAppointmentById(Long id) {


        appointmentRepository.delete(findAppointment(id));
    }


    private Appointment findAppointment(Long id)
    {

        return appointmentRepository.findById(id).orElseThrow(()->
                 new ResourceNotFoundException("Appointment not found with id: "+id )
                 );
    }

}
