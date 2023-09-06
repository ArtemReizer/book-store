package com.app.bookstore.service;

import com.app.bookstore.dto.BookDto;
import com.app.bookstore.dto.BookSearchParametersDto;
import com.app.bookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto getBookById(Long id);

    BookDto update(CreateBookRequestDto requestDto, Long id);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto parameters);
}
