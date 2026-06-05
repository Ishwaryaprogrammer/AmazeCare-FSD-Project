package com.hiretrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDto(

       // title and summary

       @NotBlank(message = "title cannot be blank")
       @NotNull(message = "title cannot be empty")
       String title,
       @NotBlank(message = "summary cannot be blank")
       @NotNull(message = "summary cannot be empty")
       String summary
) {
}
