package com.amaze.dto;


import com.amaze.enums.Specialty;

public record DoctorResDto (
        int id,
        String username,
        String fullname,
        String email,
        Specialty specialty,
        int experience,
        String qualification,
        String designation){
}

