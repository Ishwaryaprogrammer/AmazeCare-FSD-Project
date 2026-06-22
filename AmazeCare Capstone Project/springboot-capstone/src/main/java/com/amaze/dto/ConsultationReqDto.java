package com.amaze.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultationReqDto(
        @NotNull(message = "symptoms cannot be null")
        @NotBlank(message = "symptoms cannot be blank")
        String symptoms,
        @NotNull(message = "phyExam cannot be null")
        @NotBlank(message = "phyExam cannot be blank")
        String phyExam,
        @NotNull(message = "treatment cannot be null")
        @NotBlank(message = "treatment cannot be blank")
        String treatment,
        @NotNull(message = "recommended cannot be null")
        @NotBlank(message = "recommended cannot be blank")
        String recommended,
        @NotNull(message = "prescription cannot be null")
        @NotBlank(message = "prescription cannot be blank")
        String prescription
) {
}
