package com.amaze.mapper;

import com.amaze.dto.PatientReqDto;
import com.amaze.dto.PatientResDto;
import com.amaze.enums.Role;
import com.amaze.model.Patient;
import com.amaze.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientResDto mapEntityToDto(Patient patient) {
        return new PatientResDto(
                patient.getUser().getName(),
                patient.getFullName(),
                patient.getUser().getEmail(),
                patient.getDob(),
                patient.getGender(),
                patient.getContact()
        );
    }

    public User patientDtoToUser(@Valid PatientReqDto dto) {
        User user=new User();
        user.setName(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(Role.PATIENT);
        return user;
    }

    public Patient dtoToPatientEntity(@Valid PatientReqDto dto) {
        Patient patient=new Patient();
        patient.setFullName(dto.fullname());
        patient.setDob(dto.dob());
        patient.setGender(dto.gender());
        patient.setContact(dto.contact());
        return patient;
    }
}
