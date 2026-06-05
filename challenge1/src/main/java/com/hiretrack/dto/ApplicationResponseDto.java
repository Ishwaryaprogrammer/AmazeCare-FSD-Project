package com.hiretrack.dto;

import java.util.List;

public record ApplicationResponseDto(
        int totalPages,
        long totalElements,
        List<ApplicationResponse> applicationResponseList
) {
}
