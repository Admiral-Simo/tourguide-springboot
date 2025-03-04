package com.atlas.tourguide.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.atlas.tourguide.domain.entities.Tag;

public interface TagService {
	List<Tag> getTags();
	List<Tag> createTags(Set<String> tags);
	void deleteTag(UUID id);
	Tag getTagById(UUID id);
	List<Tag> getTagByIds(Set<UUID> ids);
}
