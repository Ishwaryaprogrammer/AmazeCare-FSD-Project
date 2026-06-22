package com.amaze.dto;

import com.amaze.enums.Specialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DoctorUpdateReqDto(
        @NotNull(message = "specialty cannot be empty")
        Specialty specialty,
        @NotNull(message = "experience cannot be empty")
        int experience,
        @NotBlank(message = "qualification cannot be blank")
        @Size(max=35,message = "Exceeded Maximum Length")
        String qualification,
        @NotBlank(message = "designation cannot be blank")
        @Size(max=35,message = "Exceeded Maximum Length")
        String designation
) {
}
