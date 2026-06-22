package com.amaze.dto;

import com.amaze.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentDto(
        int appointmentId,
        String patientName,
        String doctorName,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String symptoms,
        String reason,
        Status status
) {
}
