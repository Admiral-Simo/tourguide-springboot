package com.atlas.tourguide.mappers;

import com.atlas.tourguide.domain.PostStatus;
import com.atlas.tourguide.domain.dtos.CategoryDto;
import com.atlas.tourguide.domain.dtos.CreateCategoryRequest;
import com.atlas.tourguide.domain.entities.Category;
import com.atlas.tourguide.domain.entities.Post;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryMapperTest {
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    @Test
    public void toCategoryDto() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("test");
        Post post1 = new Post();
        post1.setStatus(PostStatus.PUBLISHED);
        post1.setCategory(category);
        Post post2 = new Post();
        post2.setStatus(PostStatus.PUBLISHED);
        post2.setCategory(category);
        Post post3 = new Post();
        post3.setStatus(PostStatus.DRAFT);
        post3.setCategory(category);
        List<Post> posts = List.of(post1, post2, post3);
        category.setPosts(posts);


        CategoryDto categoryDto = categoryMapper.toDto(category);

        assertThat(categoryDto.getName()).isEqualTo("test");
        assertThat(categoryDto.getId()).isEqualTo(category.getId());
        assertThat(categoryDto.getPostCount()).isEqualTo(2);
    }

    @Test
    public void toCategoryEntity() {
        var createCategoryRequest = new CreateCategoryRequest();
        createCategoryRequest.setName("test");

        Category category = categoryMapper.toEntity(createCategoryRequest);

        assertThat(category.getName()).isEqualTo("test");
        assertThat(category.getId()).isNull();
        assertThat(category.getPosts()).isNull();
    }
}
