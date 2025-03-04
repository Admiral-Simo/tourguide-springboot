package com.atlas.tourguide.services;

import java.util.List;
import java.util.UUID;

import com.atlas.tourguide.domain.entities.Post;

public interface PostService {
	List<Post> getAllPosts(UUID categoryId, UUID tagId);
}
