package com.example.lab1.persistence;

import com.example.lab1.entities.Book;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class BooksDAO {
    @Inject
    private EntityManager em;

    public void persist(Book book){
        this.em.persist(book);
    }

    public Book findOne(Long id) {
        return em.find(Book.class, id);
    }

    public Book update(Book book){
        return em.merge(book);
    }
}