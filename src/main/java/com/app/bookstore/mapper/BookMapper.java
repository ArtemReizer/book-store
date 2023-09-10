package com.app.bookstore.mapper;

import com.app.bookstore.config.MapperConfiguration;
import com.app.bookstore.dto.BookDto;
import com.app.bookstore.dto.CreateBookRequestDto;
import com.app.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
