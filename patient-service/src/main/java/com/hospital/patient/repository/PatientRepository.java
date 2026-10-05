package com.hospital.patient.repository;

import com.hospital.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access layer. Spring Data generates the implementation at runtime.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByEmail(String email);

    // Used during update: another patient (different id) must not already use this email
    boolean existsByEmailAndIdNot(String email, Long id);
}
