package com.amaze.dto;

import com.amaze.enums.Role;

public record LoginResDto(
        int id,
        String username,
        String email,
        Role role
) {
}
