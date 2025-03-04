package com.atlas.tourguide.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.tourguide.domain.dtos.PostDto;
import com.atlas.tourguide.domain.entities.Post;
import com.atlas.tourguide.domain.entities.User;
import com.atlas.tourguide.mappers.PostMapper;
import com.atlas.tourguide.services.PostService;
import com.atlas.tourguide.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	private final UserService userService;
	private final PostMapper postMapper;

	@GetMapping
	public ResponseEntity<List<PostDto>> getAllPosts(
			@RequestParam(required = false) UUID categoryId,
			@RequestParam(required = false) UUID tagId
	) {
		List<Post> posts = postService.getAllPosts(categoryId, tagId);
		List<PostDto> postDtos = posts.stream()
			.map(postMapper::toDto)
			.toList();
		 return ResponseEntity.ok(postDtos);
	}
	
	@GetMapping("/drafts")
	public ResponseEntity<List<PostDto>> getDraftPosts(
			@RequestAttribute UUID userId
	) {
		User loggedInUser = userService.getUserById(userId);
		List<Post> drafPosts = postService.getDraftPosts(loggedInUser);
		List<PostDto> postDtos = drafPosts.stream()
			.map(postMapper::toDto)
			.toList();
		return ResponseEntity.ok(postDtos);
	}
}
