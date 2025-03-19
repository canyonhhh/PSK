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

    @PostConstruct
    public void init() {
        this.loadAllAuthors();
    }

    private void loadAllAuthors() {
        this.allAuthors = authorMapper.selectAll();
    }

    @Transactional
    public String createAuthor() {
        authorMapper.insert(authorToCreate);
        return "/mybatis/authors?faces-redirect=true";
    }
}