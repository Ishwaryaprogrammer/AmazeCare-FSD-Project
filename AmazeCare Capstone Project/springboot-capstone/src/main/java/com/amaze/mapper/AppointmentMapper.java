package com.amaze.mapper;

import com.amaze.dto.*;
import com.amaze.model.Appointment;
import com.amaze.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AppointmentMapper {

    public Appointment mapDtoToEntity(AppointmentBookReqDto dto) {
        Appointment appointment=new Appointment();
        appointment.setDate(dto.date());
        appointment.setStartTime(dto.startTime());
        appointment.setEndTime(dto.endTime());
        appointment.setSymptoms(dto.symptoms());
        appointment.setReason(dto.reason());
        return appointment;
    }


    public AppointmentDto mapEntityToResDto(Appointment appointment) {
        return new AppointmentDto(
                appointment.getId(),
                appointment.getPatient().getFullName(),
                appointment.getDoctor().getFullName(),
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getSymptoms(),
                appointment.getReason(),
                appointment.getStatus()
        );
    }

    public AppointmentResDto mapListToDto(Page<Appointment> appointmentPage, List<AppointmentDto> appointmentDtoListOfPage) {
        return new AppointmentResDto(
                appointmentPage.getTotalPages(),
                appointmentPage.getTotalElements(),
                appointmentDtoListOfPage
        );
    }

    public AppointmentSlotsResDto mapAvailableSlotsToDto(List<AppointmentSlotsDto> availableSlots) {
        return new AppointmentSlotsResDto(
               availableSlots
        );
    }

    public PatientResDto mapAppointmentToPatientDto(Appointment appointment) {
        Patient patient = appointment.getPatient();
        return new PatientResDto(
                patient.getUser().getName(),
                patient.getFullName(),
                patient.getUser().getEmail(),
                patient.getDob(),
                patient.getGender(),
                patient.getContact()
        );
    }
}
