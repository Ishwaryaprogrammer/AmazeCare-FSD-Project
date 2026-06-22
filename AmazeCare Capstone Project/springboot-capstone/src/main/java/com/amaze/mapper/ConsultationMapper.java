package com.amaze.mapper;

import com.amaze.dto.ConsultationDto;
import com.amaze.dto.ConsultationReqDto;
import com.amaze.dto.ConsultationResDto;
import com.amaze.model.Consultation;
import com.amaze.model.Doctor;
import com.amaze.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsultationMapper {
    public Consultation mapDtoToEntity(ConsultationReqDto dto) {
        Consultation consultation=new Consultation();
        consultation.setSymptoms(dto.symptoms());
        consultation.setPhyExam(dto.phyExam());
        consultation.setTreatment(dto.treatment());
        consultation.setRecommended(dto.recommended());
        consultation.setPrescription(dto.prescription());
        return consultation;
    }

    public ConsultationDto mapEntityToDto(Consultation consultation) {
        Doctor doctor=consultation.getAppointment().getDoctor();
        Patient patient=consultation.getAppointment().getPatient();
        return new ConsultationDto(
                doctor.getFullName(),
                doctor.getSpecialty(),
                doctor.getDesignation(),
                doctor.getExperience(),
                doctor.getQualification(),
                patient.getFullName(),
                patient.getContact(),
                patient.getGender(),
                patient.getDob(),
                consultation.getAppointment().getDate(),
                consultation.getSymptoms(),
                consultation.getPhyExam(),
                consultation.getTreatment(),
                consultation.getRecommended(),
                consultation.getPrescription()

        );
    }

    public ConsultationResDto mapConsultationPatientDtoToResDto(List<ConsultationDto> list, Page<Consultation> consultations) {
        return new ConsultationResDto(
                consultations.getTotalPages(),
                consultations.getTotalElements(),
                list
        );
    }
}
