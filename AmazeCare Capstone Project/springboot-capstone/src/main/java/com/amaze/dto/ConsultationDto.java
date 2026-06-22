package com.amaze.dto;

import com.amaze.enums.Specialty;

import java.time.LocalDate;

public record ConsultationDto(
        String doctorName,
        Specialty doctorSpecialty,
        String doctorDesignation,
        int doctorExperience,
        String doctorQualification,
        String patientName,
        String patientContact,
        String patientGender,
        LocalDate patientDob,
        LocalDate date,
        String symptoms,
        String phyExam,
        String treatment,
        String recommended,
        String prescription
) {
}
