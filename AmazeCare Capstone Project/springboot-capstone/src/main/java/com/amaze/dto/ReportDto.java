package com.amaze.dto;

import java.time.LocalDate;

public record ReportDto(
        int id,
        String testName,
        LocalDate date,
        String fileName
) {
}
