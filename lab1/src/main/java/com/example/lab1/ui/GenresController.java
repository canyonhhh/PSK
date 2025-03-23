package com.example.lab1.ui;

import com.example.lab1.entities.Genre;
import com.example.lab1.services.GenreService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class GenresController implements Serializable {
    @Inject
    private GenreService genreService;

    @Getter
    @Setter
    private Genre selectedGenre = new Genre();

    @Getter
    private List<Genre> genres = new ArrayList<>();

    @PostConstruct
    public void init() {
        loadGenres();
    }

    private void loadGenres() {
        genres = genreService.getAllGenresWithBooks();
    }

    public void prepareNewGenre() {
        selectedGenre = new Genre();
    }

    public void selectGenre(Genre genre) {
        selectedGenre = genre;
    }

    public String saveGenre() {
        if (selectedGenre.getId() == null) {
            genreService.createGenre(selectedGenre);
        } else {
            genreService.updateGenre(selectedGenre);
        }
        selectedGenre = new Genre();
        loadGenres();
        return "genres.xhtml?faces-redirect=true";
    }
}