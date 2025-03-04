package com.atlas.tourguide.services;

import java.util.List;
import java.util.UUID;

import com.atlas.tourguide.domain.entities.Category;

public interface CategoryService {
	List<Category> listCategories();
	Category createCategory(Category category);
	void deleteCategory(UUID id);
	Category getCategoryById(UUID id);
}
