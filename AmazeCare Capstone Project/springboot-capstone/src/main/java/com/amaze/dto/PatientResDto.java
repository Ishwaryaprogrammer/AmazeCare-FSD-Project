package com.amaze.dto;

import java.time.LocalDate;

public record PatientResDto(

        String username,
        String fullname,
        String email,
        LocalDate dob,
        String gender,
        String contact

) {
}
