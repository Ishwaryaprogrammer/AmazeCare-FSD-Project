package com.amaze.service;

import com.amaze.dto.PatientReqDto;
import com.amaze.dto.PatientResDto;
import com.amaze.exception.ProfileNotFoundException;
import com.amaze.mapper.PatientMapper;
import com.amaze.model.Patient;
import com.amaze.model.User;
import com.amaze.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientService {

    private UserService userService;
    private PatientMapper patientMapper;
    private PatientRepository patientRepository;


    public PatientResDto viewProfile(String name) {
        Patient patient=getPatientByName(name);
        return patientMapper.mapEntityToDto(patient);
    }

    public Patient getPatientByName(String name){
        return patientRepository.findByUserName(name).orElseThrow(()->new ProfileNotFoundException("Profile Not found"));
    }

    public void add(@Valid PatientReqDto dto) {
        User user=patientMapper.patientDtoToUser(dto);
        Patient patient=patientMapper.dtoToPatientEntity(dto);
        userService.addUser(user);
        patient.setUser(user);
        patientRepository.save(patient);

    }
}
