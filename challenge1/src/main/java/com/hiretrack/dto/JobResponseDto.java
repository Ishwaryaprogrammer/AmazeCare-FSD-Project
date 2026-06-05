package com.hiretrack.dto;

import java.util.List;

public record JobResponseDto(
        int totalPages,
        long totalElements,
        List<JobResponse> jobResponseDtoList
) {
}
