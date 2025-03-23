package com.example.lab1.services;

import com.example.lab1.entities.Genre;
import com.example.lab1.persistence.GenresDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class GenreService {
    @Inject
    private GenresDAO genresDAO;

    @Transactional
    public void createGenre(Genre genre) {
        genresDAO.persist(genre);
    }

    public Genre getGenreById(Long id) {
        return genresDAO.findOne(id);
    }

    @Transactional
    public void updateGenre(Genre genre) {
        genresDAO.update(genre);
    }

    @Transactional
    public List<Genre> getAllGenresWithBooks() {
        return genresDAO.findAllWithBooks();
    }
}