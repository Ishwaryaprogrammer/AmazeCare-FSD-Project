package com.amaze.dto;

import java.util.List;

public record AvailabilityResDto(
        int totalPages,
        long totalElements,
        List<AvailabilityDto> list
) {
}
