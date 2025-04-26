package com.atlas.tourguide.domain.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserProfileDto(
    UUID id,
    String email,
    String name,
    LocalDateTime createdAt
) {}