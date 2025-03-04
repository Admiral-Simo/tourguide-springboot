package com.atlas.tourguide.services;

import java.util.List;
import java.util.Set;

import com.atlas.tourguide.domain.entities.Tag;

public interface TagService {
	List<Tag> getTags();
	List<Tag> createTags(Set<String> tags);
}
