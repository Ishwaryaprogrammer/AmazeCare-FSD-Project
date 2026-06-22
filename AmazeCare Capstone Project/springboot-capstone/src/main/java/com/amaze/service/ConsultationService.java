package com.amaze.service;

import com.amaze.dto.ConsultationDto;
import com.amaze.dto.ConsultationReqDto;
import com.amaze.dto.ConsultationResDto;
import com.amaze.enums.Role;
import com.amaze.enums.Specialty;
import com.amaze.enums.Status;
import com.amaze.exception.ConsultationException;
import com.amaze.exception.ResourceNotFoundException;
import com.amaze.mapper.ConsultationMapper;
import com.amaze.model.Appointment;
import com.amaze.model.Consultation;
import com.amaze.repository.ConsultationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsultationService {
    private ConsultationMapper consultationMapper;
    private ConsultationRepository consultationRepository;
    private AppointmentService appointmentService;

    public void add(int appId, ConsultationReqDto dto, String name) {
        Consultation consultation=consultationMapper.mapDtoToEntity(dto);

        Appointment appointment=appointmentService.getById(appId);
        if(appointment.getStatus().equals(Status.COMPLETED)){
            throw new ConsultationException("Consultation already completed");
        }
        if(!appointment.getDoctor().getUser().getName().contentEquals(name)){
            throw new ConsultationException(
                    "Cannot do consultation because, this appointment doesn't belongs to this doctor");
        }
        appointmentService.completeAppointment(appId);
        appointment.setStatus(Status.COMPLETED);
        consultation.setAppointment(appointment);
        consultationRepository.save(consultation);
    }

    // they can only see the consultations for completed appointments
    public ConsultationDto getByAppointmentIdForPatient(int appId, String name) {
        Consultation consultation=consultationRepository.findByAppointmentId(appId).orElseThrow(
                ()->new ResourceNotFoundException("Consultation is not done for this appointment," +
                        "Appointment is not completed"));

        if((consultation.getAppointment().getDoctor().getUser().getRole()!= Role.DOCTOR) && !consultation.getAppointment().getPatient().getUser().getName().equals(name)){
            throw new ConsultationException("Consultation doesn't belongs to this patient");
        }
        return consultationMapper.mapEntityToDto(consultation);
    }

    public ConsultationResDto getAllConsultationsByPatient(int page, int size, String name) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Consultation> consultations = consultationRepository.findAllByAppointmentPatientUserName(name, pageable);
        List<ConsultationDto> list = consultations.stream().map(consultationMapper::mapEntityToDto).toList();
        return consultationMapper.mapConsultationPatientDtoToResDto(list,consultations);
    }

    public ConsultationResDto getAllConsultationsByDoctor(int page, int size, int appId) {
        Appointment appointment=appointmentService.findByAppointmentId(appId);
        String patientName=appointment.getPatient().getUser().getName();
        return getAllConsultationsByPatient(page,size, patientName);
    }

    public ConsultationResDto getAllConsultationsByPatientBySpecialty(int page, int size, Specialty specialty, String name) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Consultation> consultations = consultationRepository.findAllByAppointmentPatientUserNameAndAppointmentDoctorSpecialty(name,specialty, pageable);
        List<ConsultationDto> list = consultations.stream().map(consultationMapper::mapEntityToDto).toList();
        return consultationMapper.mapConsultationPatientDtoToResDto(list,consultations);
    }

    public ConsultationResDto getAllConsultationsByDoctorBySpecialty(int page, int size, Specialty specialty, int appId) {
        Appointment appointment=appointmentService.findByAppointmentId(appId);
        String patientName=appointment.getPatient().getUser().getName();
        return getAllConsultationsByPatientBySpecialty(page,size,specialty, patientName);
    }
}
