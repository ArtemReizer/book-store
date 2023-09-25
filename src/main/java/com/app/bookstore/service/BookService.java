package com.app.bookstore.service;

import com.app.bookstore.dto.BookDto;
import com.app.bookstore.dto.BookSearchParametersDto;
import com.app.bookstore.dto.CreateBookRequestDto;
import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    Optional<BookDto> findBookById(Long id);

    BookDto update(CreateBookRequestDto requestDto, Long id);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto parameters);
}
