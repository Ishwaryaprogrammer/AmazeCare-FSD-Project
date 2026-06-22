package com.amaze.dto;

import com.amaze.enums.Day;

import java.time.LocalTime;

public record AvailabilityDto(
        int id,
        Day day,
        LocalTime startTime,
        LocalTime endTime,
        int duration

) {
}
