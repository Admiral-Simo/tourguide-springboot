package com.atlas.tourguide.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atlas.tourguide.domain.entities.Category;
import com.atlas.tourguide.repositories.CategoryRepository;
import com.atlas.tourguide.services.CategoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;

	@Override
	public List<Category> listCategories() {
		return categoryRepository.findAllWithPostCount();
	}

	@Override
	@Transactional
	public Category createCategory(Category category) {
		String name = category.getName();
		boolean exists = categoryRepository.existsByNameIgnoreCase(name);
		if (exists) {
			throw new IllegalArgumentException("Category already exists with name: " + name);
		}
		// TODO Auto-generated method stub
		return categoryRepository.save(category);
	}

}
