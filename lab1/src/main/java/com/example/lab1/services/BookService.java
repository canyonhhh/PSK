package com.example.lab1.services;

import com.example.lab1.entities.Book;
import com.example.lab1.persistence.BooksDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@RequestScoped
public class BookService {
    @Inject
    private BooksDAO booksDAO;

    @Transactional
    public void createBook(Book book) {
        booksDAO.persist(book);
    }

    public Book getBookById(Long id) {
        return booksDAO.findOne(id);
    }

    @Transactional
    public Book updateBook(Book book) {
        return booksDAO.update(book);
    }
}