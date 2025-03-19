package com.example.lab1.ui;

import com.example.lab1.entities.Book;
import com.example.lab1.entities.Genre;
import com.example.lab1.services.GenreService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class GenreBooksController implements Serializable {
    @Inject
    private GenreService genreService;

    @Inject
    private EntityManager em;

    @Getter
    @Setter
    private Long genreId;

    @Getter
    private List<Book> books = new ArrayList<>();

    @Getter
    private String genreName = "";

    public void loadGenreBooks() {
        if (genreId != null) {
            Genre genre = genreService.getGenreById(genreId);
            if (genre != null) {
                genreName = genre.getName();

                // We need to fetch the books with the author to avoid LazyInitializationException
                books = em.createQuery(
                                "SELECT DISTINCT b FROM Book b " +
                                        "JOIN FETCH b.author " +
                                        "JOIN b.genres g " +
                                        "WHERE g.id = :genreId", Book.class)
                        .setParameter("genreId", genreId)
                        .getResultList();
            }
        }
    }
}