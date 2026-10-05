package com.hospital.patient.service;

import com.hospital.patient.dto.PatientRequest;
import com.hospital.patient.dto.PatientResponse;
import com.hospital.patient.entity.Patient;
import com.hospital.patient.exception.DuplicateResourceException;
import com.hospital.patient.exception.ResourceNotFoundException;
import com.hospital.patient.mapper.PatientMapper;
import com.hospital.patient.repository.PatientRepository;
import com.hospital.patient.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the service layer. The repository is mocked, so no database is needed.
 */
@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    // Real mapper (it has no dependencies), wrapped in a spy
    @Spy
    private PatientMapper patientMapper = new PatientMapper();

    @InjectMocks
    private PatientServiceImpl patientService;

    private final PatientRequest request =
            new PatientRequest("Bhanu", "Bhanu@Gmail.com", LocalDate.of(2002, 5, 10), "Hyderabad");

    @Test
    void createPatient_savesPatientWithLowerCaseEmail() {
        when(patientRepository.existsByEmail("bhanu@gmail.com")).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenAnswer(inv -> inv.getArgument(0));

        PatientResponse response = patientService.createPatient(request);

        assertThat(response.email()).isEqualTo("bhanu@gmail.com");
        assertThat(response.name()).isEqualTo("Bhanu");
    }

    @Test
    void createPatient_throwsWhenEmailAlreadyExists() {
        when(patientRepository.existsByEmail("bhanu@gmail.com")).thenReturn(true);

        assertThatThrownBy(() -> patientService.createPatient(request))
                .isInstanceOf(DuplicateResourceException.class);
        verify(patientRepository, never()).save(any());
    }

    @Test
    void getPatientById_throwsWhenNotFound() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.getPatientById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void updatePatient_updatesFields() {
        Patient existing = Patient.builder().id(1L).name("Old").email("old@gmail.com")
                .dateOfBirth(LocalDate.of(2000, 1, 1)).address("Old address").build();
        when(patientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(patientRepository.existsByEmailAndIdNot("bhanu@gmail.com", 1L)).thenReturn(false);

        PatientResponse response = patientService.updatePatient(1L, request);

        assertThat(response.name()).isEqualTo("Bhanu");
        assertThat(response.email()).isEqualTo("bhanu@gmail.com");
        assertThat(response.address()).isEqualTo("Hyderabad");
    }

    @Test
    void deletePatient_throwsWhenNotFound() {
        when(patientRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.deletePatient(5L))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(patientRepository, never()).delete(any());
    }
}
