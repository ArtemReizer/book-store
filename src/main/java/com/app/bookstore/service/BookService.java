package com.app.bookstore.service;

import com.app.bookstore.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
