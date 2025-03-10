package com.atlas.tourguide.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.atlas.tourguide.domain.entities.Category;
import com.atlas.tourguide.repositories.CategoryRepository;
import com.atlas.tourguide.services.CategoryService;

import jakarta.persistence.EntityNotFoundException;
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

	@Override
	public void deleteCategory(UUID id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			if (!category.get().getPosts().isEmpty()) {
				throw new IllegalStateException("Category has posts associated with it");
			}
			categoryRepository.deleteById(id);
		}
	}

	@Override
	public Category getCategoryById(UUID id) {
		return categoryRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
	}
}
