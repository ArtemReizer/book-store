package com.app.bookstore.mapper;

import com.app.bookstore.config.MapperConfig;
import com.app.bookstore.dto.BookDto;
import com.app.bookstore.dto.BookDtoWithoutCategoryIds;
import com.app.bookstore.dto.CreateBookRequestDto;
import com.app.bookstore.model.Book;
import com.app.bookstore.model.Category;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toEntity(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategoryIds(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoriesIds(
                book.getCategories().stream()
                        .map(Category::getId)
                        .collect(Collectors.toSet())
        );
    }
}
