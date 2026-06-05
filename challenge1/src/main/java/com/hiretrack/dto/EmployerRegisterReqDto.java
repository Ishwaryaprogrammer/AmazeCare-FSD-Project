package com.hiretrack.dto;

import com.hiretrack.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployerRegisterReqDto (
    @NotBlank(message = "UserName cannot be blank")
    @NotNull(message = "UserName cannot be empty")
    @Size(max=20,message = "Exceeded Maximum Length")
    String username,
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be empty")
    @Size(max=20,message = "Exceeded Maximum Length")
    String password,
    @NotBlank(message = "CompanyName cannot be blank")
    @NotNull(message = "CompanyName cannot be empty")
   String companyName
){
}
