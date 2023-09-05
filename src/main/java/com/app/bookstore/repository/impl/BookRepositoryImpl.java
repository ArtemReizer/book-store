package com.app.bookstore.repository.impl;

import com.app.bookstore.exceptions.EntityNotFoundException;
import com.app.bookstore.model.Book;
import com.app.bookstore.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new EntityNotFoundException("Can't save the book: " + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Book ", Book.class).getResultList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Can't get all books", e);
        }
    }

    @Override
    public Book getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Book.class, id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Cannot get book by id: " + id, e);
        }
    }
}
