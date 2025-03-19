package com.example.lab1.ui.mybatis;

import com.example.lab1.mybatis.dao.GenreMapper;
import com.example.lab1.mybatis.model.Genre;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class GenresMyBatis {
    @Inject
    private GenreMapper genreMapper;

    @Getter
    private List<Genre> allGenres;

    @Getter @Setter
    private Genre genreToCreate = new Genre();

    @Getter @Setter
    private Long selectedGenreId;

    @PostConstruct
    public void init() {
        loadAllGenres();

        // if theres a selected genre ID, load that genre
        if (selectedGenreId != null) {
            genreToCreate = genreMapper.selectByPrimaryKey(selectedGenreId);
        }
    }

    private void loadAllGenres() {
        allGenres = genreMapper.selectAll();
    }

    public void prepareNewGenre() {
        genreToCreate = new Genre();
        selectedGenreId = null;
    }

    public void selectGenre(Genre genre) {
        genreToCreate = genre;
        selectedGenreId = genre.getId();
    }

    @Transactional
    public String saveGenre() {
        if (genreToCreate.getId() == null) {
            genreMapper.insert(genreToCreate);
        } else {
            genreMapper.updateByPrimaryKey(genreToCreate);
        }

        genreToCreate = new Genre();
        selectedGenreId = null;
        loadAllGenres();

        return "/mybatis/genres?faces-redirect=true";
    }
}