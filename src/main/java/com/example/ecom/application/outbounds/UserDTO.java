package com.example.ecom.application.outbounds;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDTO(
        Long id,
        String email,
        String firstName,
        String lastName,
        String createdAt,
        String updatedAt
) {}
