package com.amaze.dto;

import java.util.List;

public record ReportResDto(
        int totalPage,
        long totalElements,
        List<ReportDto> list
) {
}
