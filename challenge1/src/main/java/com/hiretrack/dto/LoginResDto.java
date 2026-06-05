package com.hiretrack.dto;

import com.hiretrack.enums.Role;

public record LoginResDto(
        int id,
        String username,
        Role role
) {
}
