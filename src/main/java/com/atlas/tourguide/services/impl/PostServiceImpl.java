package com.atlas.tourguide.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atlas.tourguide.domain.PostStatus;
import com.atlas.tourguide.domain.entities.Category;
import com.atlas.tourguide.domain.entities.Post;
import com.atlas.tourguide.domain.entities.Tag;
import com.atlas.tourguide.domain.entities.User;
import com.atlas.tourguide.repositories.PostRepository;
import com.atlas.tourguide.services.CategoryService;
import com.atlas.tourguide.services.PostService;
import com.atlas.tourguide.services.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;
	private final CategoryService categoryService;
	private final TagService tagService;

	@Transactional(readOnly = true)
	@Override
	public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
		if (categoryId != null && tagId != null) {
			Category category =  categoryService.getCategoryById(categoryId);
			Tag tag =  tagService.getTagById(tagId);
			return postRepository.findAllByStatusAndCategoryAndTagsContaining(PostStatus.PUBLISHED, category, tag);
		}
		if (categoryId != null) {
			Category category =  categoryService.getCategoryById(categoryId);
			return postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED, category);
		}
		if (tagId != null) {
			Tag tag =  tagService.getTagById(tagId);
			return postRepository.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED, tag);
		}
		return postRepository.findAllByStatus(PostStatus.PUBLISHED);
	}

	@Override
	public List<Post> getDraftPosts(User user) {
		return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
	}

}
