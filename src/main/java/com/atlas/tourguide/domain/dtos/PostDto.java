package com.atlas.tourguide.domain.dtos;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.atlas.tourguide.domain.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
	private UUID id;
	private String title;
	private String content;
	// TODO: author
	private AuthorDto author;
	private CategoryDto category;
	private Set<TagDto> tags;
	private Integer readingTime;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
    private Double latitude;  // Latitude of the post location
    private Double longitude; // Longitude of the post location
	private PostStatus status;
}
