package com.amaze.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record PatientReqDto(

        @NotBlank(message = "User Name cannot be blank")
        @NotNull(message = "User Name cannot be empty")
        @Size(max=20,message = "Exceeded Maximum Length")

        String username,
        @NotBlank(message = "Name cannot be blank")
        @NotNull(message = "Name cannot be empty")
        @Size(max=20,message = "Exceeded Maximum Length")

        String fullname,
        @NotBlank(message = "Mail cannot be blank")
        @NotNull(message = "Mail cannot be empty")
        @Size(max=35,message = "Exceeded Maximum Length")
        String email,
        @NotBlank(message = "Password cannot be blank")
        @NotNull(message = "Password cannot be empty")
        @Size(max=20,message = "Exceeded Maximum Length")
        String password,


        @NotNull(message = "Select DOB")
        LocalDate dob,
        @NotBlank(message = "Gender is Required")
        @NotNull(message = "Gender is Required")
        String gender,
        @NotBlank(message = "Contact cannot be blank")
        @NotNull(message = "Contact cannot be empty")
        String contact
) {
}
