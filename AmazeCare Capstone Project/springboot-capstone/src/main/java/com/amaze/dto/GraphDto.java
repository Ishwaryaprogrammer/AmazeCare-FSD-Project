package com.amaze.dto;

import java.util.List;

public record GraphDto(
        String title,
        List<String> label,
        List<Long> data
) {
}
