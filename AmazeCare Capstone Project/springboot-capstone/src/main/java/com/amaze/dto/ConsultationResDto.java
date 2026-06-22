package com.amaze.dto;

import java.util.List;

public record ConsultationResDto (
        int totalPages,
        long totalElements,
        List<ConsultationDto> list
){
}
