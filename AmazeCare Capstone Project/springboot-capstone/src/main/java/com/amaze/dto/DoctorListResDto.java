package com.amaze.dto;

import java.util.List;

public record DoctorListResDto(
        int totalPages,
        long totalElements,
        List<DoctorResDto> doctorsList

) {
}
