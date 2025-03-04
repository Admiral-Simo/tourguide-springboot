package com.atlas.tourguide.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atlas.tourguide.domain.entities.Tag;
import com.atlas.tourguide.repositories.TagRepository;
import com.atlas.tourguide.services.TagService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
	private final TagRepository tagRepository;

	@Override
	public List<Tag> getTags() {
		// TODO Auto-generated method stub
		return tagRepository.findAllWithPostCount();
	}

	@Transactional
	@Override
	public List<Tag> createTags(Set<String> tagNames) {
		List<Tag> existingTags = tagRepository.findByNameIn(tagNames);
		Set<String> existingTagsNames = existingTags.stream()
				.map(Tag::getName)
				.collect(Collectors.toSet());
		// TODO Auto-generated method stub
		List<Tag> newTags = tagNames.stream()
			.filter(name -> !existingTagsNames.contains(name))
			.map(name -> Tag.builder()
					.name(name)
					.posts(new HashSet<>())
					.build())
			.collect(Collectors.toList());
		List<Tag> savedTags = new ArrayList<>();
		if (!newTags.isEmpty()) {
			savedTags = tagRepository.saveAll(newTags);
		}
		savedTags.addAll(existingTags);
		return savedTags;
	}
}
