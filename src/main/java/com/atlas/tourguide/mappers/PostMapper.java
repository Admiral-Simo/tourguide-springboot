package com.atlas.tourguide.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.atlas.tourguide.domain.CreatePostRequest;
import com.atlas.tourguide.domain.UpdatePostRequest;
import com.atlas.tourguide.domain.dtos.CreatePostRequestDto;
import com.atlas.tourguide.domain.dtos.PostDto;
import com.atlas.tourguide.domain.dtos.UpdatePostRequestDto;
import com.atlas.tourguide.domain.entities.Post;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
	@Mapping(target = "author", source = "author")
	@Mapping(target = "category", source = "category")
	@Mapping(target = "tags", source = "tags")
	PostDto toDto(Post post);
	
	CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);
	UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);
}
