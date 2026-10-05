package com.hospital.patient.service;

import com.hospital.patient.dto.PageResponse;
import com.hospital.patient.dto.PatientRequest;
import com.hospital.patient.dto.PatientResponse;
import org.springframework.data.domain.Pageable;

/**
 * Business operations for patients.
 * The controller depends on this interface, not on the implementation.
 */
public interface PatientService {

    PatientResponse createPatient(PatientRequest request);

    PatientResponse getPatientById(Long id);

    PageResponse<PatientResponse> getAllPatients(Pageable pageable);

    PatientResponse updatePatient(Long id, PatientRequest request);

    void deletePatient(Long id);
}
