package com.example.lab1.persistence;

import com.example.lab1.entities.Book;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

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

    public List<Book> findBooksByGenreId(Long genreId) {
        return em.createQuery(
                        "SELECT DISTINCT b FROM Book b " +
                                "JOIN FETCH b.author " +
                                "JOIN b.genres g " +
                                "WHERE g.id = :genreId", Book.class)
                .setParameter("genreId", genreId)
                .getResultList();
    }
}