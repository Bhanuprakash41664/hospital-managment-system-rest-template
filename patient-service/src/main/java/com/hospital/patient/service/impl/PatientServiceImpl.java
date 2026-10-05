package com.hospital.patient.service.impl;

import com.hospital.patient.dto.PageResponse;
import com.hospital.patient.dto.PatientRequest;
import com.hospital.patient.dto.PatientResponse;
import com.hospital.patient.entity.Patient;
import com.hospital.patient.exception.DuplicateResourceException;
import com.hospital.patient.exception.ResourceNotFoundException;
import com.hospital.patient.mapper.PatientMapper;
import com.hospital.patient.repository.PatientRepository;
import com.hospital.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
// Every public method runs in a transaction; read-only methods override this below.
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public PatientResponse createPatient(PatientRequest request) {
        String email = PatientMapper.normalizeEmail(request.email());
        log.info("Creating patient with email {}", email);

        // Business rule: email must be unique
        if (patientRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Patient already exists with email: " + email);
        }

        Patient saved = patientRepository.save(patientMapper.toEntity(request));
        log.info("Created patient with id {}", saved.getId());
        return patientMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        return patientMapper.toResponse(findPatientOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PatientResponse> getAllPatients(Pageable pageable) {
        return PageResponse.from(patientRepository.findAll(pageable).map(patientMapper::toResponse));
    }

    @Override
    public PatientResponse updatePatient(Long id, PatientRequest request) {
        log.info("Updating patient with id {}", id);
        Patient patient = findPatientOrThrow(id);

        String email = PatientMapper.normalizeEmail(request.email());
        if (patientRepository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateResourceException("Another patient already uses email: " + email);
        }

        patientMapper.updateEntity(patient, request);
        // No explicit save() needed: the entity is managed, so changes are flushed at commit.
        return patientMapper.toResponse(patient);
    }

    @Override
    public void deletePatient(Long id) {
        log.info("Deleting patient with id {}", id);
        patientRepository.delete(findPatientOrThrow(id));
    }

    // Shared helper: load a patient or throw a 404-style exception
    private Patient findPatientOrThrow(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }
}
