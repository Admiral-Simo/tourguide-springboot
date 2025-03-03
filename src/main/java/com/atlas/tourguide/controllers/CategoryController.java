package com.atlas.tourguide.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.tourguide.domain.dtos.CategoryDto;
import com.atlas.tourguide.domain.dtos.CreateCategoryRequest;
import com.atlas.tourguide.domain.entities.Category;
import com.atlas.tourguide.mappers.CategoryMapper;
import com.atlas.tourguide.services.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;

	@GetMapping
	public ResponseEntity<List<CategoryDto>> listCategories() {
		List<CategoryDto> categories = categoryService.listCategories()
				.stream().map(categoryMapper::toDto)
				.toList();
		return ResponseEntity.ok(categories);
	}

	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
		Category category = categoryMapper.toEntity(createCategoryRequest);
		Category savedCategory = categoryService.createCategory(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(
			categoryMapper.toDto(savedCategory)
			
		);
	}
}
