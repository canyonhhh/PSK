package com.example.lab1.ui;

import com.example.lab1.entities.Author;
import com.example.lab1.services.AuthorService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class AuthorsController implements Serializable {
    @Inject
    private AuthorService authorService;

    @Setter
    @Getter
    private Author selectedAuthor = new Author();
    @Setter
    @Getter
    private List<Author> authors = new ArrayList<>();

    @PostConstruct
    public void init() {
        loadAuthors();
    }

    private void loadAuthors() {
        authors = authorService.getAllAuthors();
    }

    public String saveAuthor() {
        if (selectedAuthor.getId() == null) {
            return createAuthor();
        } else {
            return updateAuthor();
        }
    }

    public String createAuthor() {
        authorService.createAuthor(selectedAuthor);
        selectedAuthor = new Author();
        loadAuthors();
        return "authors.xhtml?faces-redirect=true";
    }

    public String updateAuthor() {
        authorService.updateAuthor(selectedAuthor);
        selectedAuthor = new Author();
        loadAuthors();
        return "authors.xhtml?faces-redirect=true";
    }

    public void prepareNewAuthor() {
        selectedAuthor = new Author();
    }

    public void selectAuthor(Author author) {
        selectedAuthor = author;
    }
}