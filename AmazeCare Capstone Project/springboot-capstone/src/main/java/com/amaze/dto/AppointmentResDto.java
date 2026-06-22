package com.amaze.dto;

import java.util.List;

public record AppointmentResDto(
        int totalPages,
        long totalElements,
        List<AppointmentDto> appointmentList
) {
}
