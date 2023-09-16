package com.app.bookstore.mapper;

import com.app.bookstore.dto.CategoryDto;
import com.app.bookstore.model.Category;

public interface CategoryMapper {
    CategoryDto toDto(Category category);

    CategoryDto toEntity(CategoryDto categoryDto);
}
