package com.atlas.tourguide.services;

import java.util.List;
import java.util.UUID;

import com.atlas.tourguide.domain.CreatePostRequest;
import com.atlas.tourguide.domain.entities.Post;
import com.atlas.tourguide.domain.entities.User;

public interface PostService {
	List<Post> getAllPosts(UUID categoryId, UUID tagId);
	List<Post> getDraftPosts(User user);
	Post createPost(User user, CreatePostRequest createPostRequest);
}
