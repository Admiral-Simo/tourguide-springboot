package com.atlas.tourguide.domain.dtos;

import java.util.UUID;

public record CategoryDto(UUID id, String name, long postCount) {

}
