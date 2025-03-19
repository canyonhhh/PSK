package com.example.lab1.ui.mybatis;

import com.example.lab1.mybatis.dao.AuthorMapper;
import com.example.lab1.mybatis.model.Author;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class AuthorsMyBatis {
    @Inject
    private AuthorMapper authorMapper;

    @Getter
    private List<Author> allAuthors;

    @Getter @Setter
    private Author authorToCreate = new Author();

    @Getter @Setter
    private Long selectedAuthorId;

    @PostConstruct
    public void init() {
        this.loadAllAuthors();

        // If there's a selected author ID, load that author
        if (selectedAuthorId != null) {
            authorToCreate = authorMapper.selectByPrimaryKey(selectedAuthorId);
        }
    }

    private void loadAllAuthors() {
        this.allAuthors = authorMapper.selectAll();
    }

    public void prepareNewAuthor() {
        authorToCreate = new Author();
        selectedAuthorId = null;
    }

    public void selectAuthor(Author author) {
        authorToCreate = author;
        selectedAuthorId = author.getId();
    }

    @Transactional
    public String createAuthor() {
        if (authorToCreate.getId() == null) {
            authorMapper.insert(authorToCreate);
        } else {
            authorMapper.updateByPrimaryKey(authorToCreate);
        }

        // Reset after creating/updating
        authorToCreate = new Author();
        selectedAuthorId = null;
        loadAllAuthors();

        return "/mybatis/authors?faces-redirect=true";
    }
}