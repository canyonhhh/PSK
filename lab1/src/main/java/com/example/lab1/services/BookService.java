package com.example.lab1.services;

import com.example.lab1.entities.Book;
import com.example.lab1.persistence.BooksDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class BookService {
    @Inject
    private BooksDAO booksDAO;

    @Transactional
    public void createBook(Book book) {
        booksDAO.persist(book);
    }

    @Transactional
    public List<Book> getAllBooks() {
        return booksDAO.findAll();
    }

    public Book getBookById(Long id) {
        return booksDAO.findOne(id);
    }

    @Transactional
    public void updateBook(Book book) {
        booksDAO.update(book);
    }

    public List<Book> getBooksByGenreId(Long genreId) {
        return booksDAO.findBooksByGenreId(genreId);
    }
}