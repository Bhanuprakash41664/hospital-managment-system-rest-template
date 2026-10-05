package com.hospital.appointment.client;

import com.hospital.appointment.dto.PatientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PatientClient {

    private final RestTemplate restTemplate;

    @Value("${patient.service.url}")
    private String patientServiceUrl;

    public PatientResponse getPatientById(Long id)
    {
        String url =
                patientServiceUrl
                        + "/api/v1/patients/"
                        + id;

        return  restTemplate.getForObject(url, PatientResponse.class);

    }

}
