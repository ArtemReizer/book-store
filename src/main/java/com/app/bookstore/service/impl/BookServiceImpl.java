package com.app.bookstore.service.impl;

import com.app.bookstore.dto.BookDto;
import com.app.bookstore.dto.BookSearchParametersDto;
import com.app.bookstore.dto.CreateBookRequestDto;
import com.app.bookstore.exceptions.EntityNotFoundException;
import com.app.bookstore.mapper.BookMapper;
import com.app.bookstore.model.Book;
import com.app.bookstore.repository.SpecificationBuilder;
import com.app.bookstore.repository.book.BookRepository;
import com.app.bookstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final SpecificationBuilder<Book> specificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findBookById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book with id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto update(CreateBookRequestDto requestDto, Long id) {
        Book book = bookMapper.toModel(requestDto);
        book.setId(id);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto params) {
        Specification<Book> specification = specificationBuilder.build(params);
        return bookRepository.findAll(specification).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
