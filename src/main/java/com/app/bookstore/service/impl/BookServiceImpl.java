package com.app.bookstore.service.impl;

import com.app.bookstore.dto.BookDto;
import com.app.bookstore.dto.CreateBookRequestDto;
import com.app.bookstore.exceptions.EntityNotFoundException;
import com.app.bookstore.mapper.BookMapper;
import com.app.bookstore.model.Book;
import com.app.bookstore.repository.BookRepository;
import com.app.bookstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book with id " + id)));
    }
}
