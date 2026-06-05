package com.hiretrack.dto;

import com.hiretrack.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SeekerRegisterReqDto (
    @NotBlank(message = "Username cannot be blank")
    @NotNull(message = "Username cannot be empty")
    @Size(max=20,message = "Exceeded Maximum Length")
    String username,
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be empty")
    @Size(max=20,message = "Exceeded Maximum Length")
    String password,
    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be empty")
    String name,
    @NotBlank(message = "resumeSummary cannot be blank")
    @NotNull(message = "resumeSummary cannot be empty")
    String resumeSummary
    ){

}
