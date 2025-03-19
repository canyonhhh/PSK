package com.example.lab1.services;

import com.example.lab1.entities.Author;
import com.example.lab1.persistence.AuthorsDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class AuthorService {
    @Inject
    private AuthorsDAO authorsDAO;

    public List<Author> getAllAuthors() {
        return authorsDAO.loadAll();
    }

    @Transactional
    public void createAuthor(Author author) {
        authorsDAO.persist(author);
    }

    public Author getAuthorById(Long id) {
        return authorsDAO.findOne(id);
    }

    @Transactional
    public void updateAuthor(Author author) {
        authorsDAO.update(author);
    }
}