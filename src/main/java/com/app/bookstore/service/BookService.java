package com.app.bookstore.service;

import com.app.bookstore.dto.BookDto;
import com.app.bookstore.dto.BookDtoWithoutCategoryIds;
import com.app.bookstore.dto.BookSearchParametersDto;
import com.app.bookstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    BookDto update(CreateBookRequestDto requestDto, Long id);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> search(BookSearchParametersDto parameters);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long id);
}
