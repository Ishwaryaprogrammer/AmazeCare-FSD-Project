package com.amaze.dto;

import com.amaze.enums.Day;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record AvailabilityReqDto(
        @NotNull(message = "day cannot be empty")
        Day day,
        @NotNull(message = "startTime cannot be empty")
        LocalTime startTime,
        @NotNull(message = "endTime cannot be empty")
        LocalTime endTime,
        @NotNull(message = "duration cannot be empty")
        int duration
) {
}
