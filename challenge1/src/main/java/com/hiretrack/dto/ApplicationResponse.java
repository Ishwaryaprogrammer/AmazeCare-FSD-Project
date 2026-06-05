package com.hiretrack.dto;

import java.time.Instant;

public record ApplicationResponse(
        int id,
        Instant appliedAt,
        String jobTitle,
        String companyName
        ) {
}
