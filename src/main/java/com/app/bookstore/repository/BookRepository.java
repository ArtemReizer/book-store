package com.app.bookstore.repository;

import com.app.bookstore.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();

    Book getById(Long id);
}
