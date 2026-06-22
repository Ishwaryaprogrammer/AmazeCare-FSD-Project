package com.amaze.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentBookReqDto(
        @NotNull(message = "date cannot be null")
         LocalDate date,
        @NotNull(message = "startTime cannot be null")
         LocalTime startTime,
        @NotNull(message = "endTime cannot be null")
        LocalTime endTime,
        @NotBlank(message = "symptoms cannot be blank")
        @NotNull(message = "symptoms cannot be null")
         String symptoms,
        @NotBlank(message = "reason cannot be blank")
        @NotNull(message = "reason cannot be null")
         String reason
) {
}
