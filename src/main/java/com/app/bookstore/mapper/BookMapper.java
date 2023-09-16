package com.app.bookstore.mapper;

import com.app.bookstore.config.MapperConfig;
import com.app.bookstore.dto.BookDto;
import com.app.bookstore.dto.BookDtoWithoutCategoryIds;
import com.app.bookstore.dto.CreateBookRequestDto;
import com.app.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toEntity(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategoryIds(Book book);
}
