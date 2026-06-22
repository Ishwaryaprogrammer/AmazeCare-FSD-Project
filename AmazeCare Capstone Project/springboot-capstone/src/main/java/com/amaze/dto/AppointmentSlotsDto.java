package com.amaze.dto;

import java.time.LocalTime;

public record AppointmentSlotsDto(
        LocalTime startTime,
        LocalTime endTime,
        int duration
) {
}
