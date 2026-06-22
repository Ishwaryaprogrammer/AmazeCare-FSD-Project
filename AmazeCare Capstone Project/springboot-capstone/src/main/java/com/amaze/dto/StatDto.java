package com.amaze.dto;

import java.util.List;

public record StatDto(
        List<String> label,
        List<Long> count
) {
}
