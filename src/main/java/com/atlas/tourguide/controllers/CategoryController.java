package com.atlas.tourguide.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.tourguide.domain.dtos.CategoryDto;
import com.atlas.tourguide.mappers.CategoryMapper;
import com.atlas.tourguide.services.CategoryService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;

	@GetMapping
	public ResponseEntity<List<CategoryDto>> getMethodName() {
		List<CategoryDto> categories = categoryService.listCategories()
				.stream().map(categoryMapper::toDto)
				.toList();
		return ResponseEntity.ok(categories);
	}

}
