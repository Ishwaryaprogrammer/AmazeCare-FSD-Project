package com.amaze.mapper;

import com.amaze.dto.*;
import com.amaze.enums.Role;
import com.amaze.model.Doctor;
import com.amaze.model.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoctorMapper {
    public static DoctorResDto mapEntityToDto(Doctor doctor) {
        return new DoctorResDto(
                doctor.getId(),
                doctor.getUser().getName(),
                doctor.getFullName(),
                doctor.getUser().getEmail(),
                doctor.getSpecialty(),
                doctor.getExperience(),
                doctor.getQualification(),
                doctor.getDesignation()
        );
    }

    public static Doctor doctorDtoToDoctorEntity(@Valid DoctorReqDto dto) {
        Doctor doctor=new Doctor();
        doctor.setFullName(dto.fullname());
        doctor.setSpecialty(dto.specialty());
        doctor.setExperience(dto.experience());
        doctor.setQualification(dto.qualification());
        doctor.setDesignation(dto.designation());
        return doctor;
    }

    public Doctor mapUpdateReqDtoToEntity(Doctor doctor, DoctorUpdateReqDto dto) {

        doctor.setSpecialty(dto.specialty());
        doctor.setExperience(dto.experience());
        doctor.setQualification(dto.qualification());
        doctor.setDesignation(dto.designation());
        return doctor;
    }

    public DoctorListResDto mapPageToDto(Page<Doctor> pages) {
        List<DoctorResDto> list = pages.getContent().stream().map(DoctorMapper::mapEntityToDto).toList();
        return new DoctorListResDto(
                pages.getTotalPages(),
                pages.getTotalElements(),
                list
        );
    }

    public static User doctorDtoToUser(DoctorReqDto dto) {
        User user=new User();
        user.setName(dto.username());
        user.setEmail(dto.email());
        user.setRole(Role.DOCTOR);
        return user;
    }
}
