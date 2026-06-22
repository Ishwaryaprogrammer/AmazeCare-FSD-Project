package com.amaze.dto;

import com.amaze.enums.Specialty;

import java.util.List;

public record SpecialtyDto(
        List<Specialty> specialtyList
) {
}
