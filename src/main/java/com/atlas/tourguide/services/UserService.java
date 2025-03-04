package com.atlas.tourguide.services;

import java.util.UUID;

import com.atlas.tourguide.domain.entities.User;

public interface UserService {
	User getUserById(UUID id);
}
