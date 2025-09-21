package com.atlas.tourguide.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.tourguide.domain.dtos.CreateTagsRequest;
import com.atlas.tourguide.domain.dtos.TagDto;
import com.atlas.tourguide.domain.entities.Tag;
import com.atlas.tourguide.mappers.TagMapper;
import com.atlas.tourguide.services.TagService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
  private final TagMapper tagMapper;
  private final TagService tagService;

  @GetMapping
  public ResponseEntity<List<TagDto>> getAllTags() {
    List<Tag> tags = tagService.getTags();
    List<TagDto> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();
    return ResponseEntity.ok(tagResponses);
  }

  @PostMapping
  public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagsRequest createTagsRequest) {
    // TODO: process POST request
    List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());
    List<TagDto> tagResponses = savedTags.stream().map(tag -> tagMapper.toTagResponse(tag))
        .toList();
    return ResponseEntity.status(HttpStatus.CREATED).body(tagResponses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> createTags(@PathVariable UUID id) {
    tagService.deleteTag(id);
    return ResponseEntity.noContent().build();
  }
}
