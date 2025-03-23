package com.example.lab1.ui;

import com.example.lab1.entities.Book;
import com.example.lab1.entities.Genre;
import com.example.lab1.services.BookService;
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
    private BookService bookService;

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
                books = bookService.getBooksByGenreId(genreId);
            }
        }
    }
}