package com.hiretrack.dto;

import com.hiretrack.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterReqDto(
        @NotBlank(message = "UserName cannot be blank")
        @NotNull(message = "UserName cannot be empty")
        @Size(max=20,message = "Exceeded Maximum Length")
        String username,
        @NotBlank(message = "Password cannot be blank")
        @NotNull(message = "Password cannot be empty")
        @Size(max=20,message = "Exceeded Maximum Length")
        String password,
        @NotNull(message = "Role cannot be empty")
        Role role

) {
}
