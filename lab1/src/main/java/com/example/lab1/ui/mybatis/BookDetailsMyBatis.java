package com.example.lab1.ui.mybatis;

import com.example.lab1.mybatis.dao.AuthorMapper;
import com.example.lab1.mybatis.dao.BookMapper;
import com.example.lab1.mybatis.dao.GenreMapper;
import com.example.lab1.mybatis.model.Author;
import com.example.lab1.mybatis.model.Book;
import com.example.lab1.mybatis.model.Genre;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class BookDetailsMyBatis {
    @Inject
    private BookMapper bookMapper;

    @Inject
    private AuthorMapper authorMapper;

    @Inject
    private GenreMapper genreMapper;

    @Getter @Setter
    private Long bookId;

    @Getter
    private Book book;

    @Getter
    private Author author;

    @Getter
    private List<Genre> bookGenres = new ArrayList<>();

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String bookIdParam = params.get("bookId");
        if (bookIdParam != null && !bookIdParam.isEmpty()) {
            this.bookId = Long.parseLong(bookIdParam);
            loadBook();
        }
    }

    private void loadBook() {
        if (bookId != null) {
            book = bookMapper.selectByPrimaryKey(bookId);
            if (book != null) {
                author = authorMapper.selectByPrimaryKey(book.getAuthorId());
                bookGenres = genreMapper.selectByBookId(bookId);
            }
        }
    }

    public String getAuthorName() {
        if (author != null) {
            return author.getFirstName() + " " + author.getLastName();
        }
        return "No author assigned";
    }

    public boolean hasGenres() {
        return bookGenres != null && !bookGenres.isEmpty();
    }
}