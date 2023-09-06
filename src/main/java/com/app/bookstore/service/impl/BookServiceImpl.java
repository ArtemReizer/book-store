package com.app.bookstore.service.impl;

import com.app.bookstore.model.Book;
import com.app.bookstore.repository.BookRepository;
import com.app.bookstore.service.BookService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
