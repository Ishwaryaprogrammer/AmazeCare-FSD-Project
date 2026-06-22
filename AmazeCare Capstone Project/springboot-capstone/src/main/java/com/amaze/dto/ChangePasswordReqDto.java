package com.amaze.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordReqDto(
        @NotBlank(message = "Password cannot be blank")
        @NotNull(message = "Password cannot be empty")
        String password
) {
}
