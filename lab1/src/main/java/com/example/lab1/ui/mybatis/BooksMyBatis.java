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
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Model
public class BooksMyBatis {
    @Inject
    private BookMapper bookMapper;

    @Inject
    private AuthorMapper authorMapper;

    @Inject
    private GenreMapper genreMapper;

    @Getter @Setter
    private Long authorId;

    @Getter @Setter
    private Long bookId;

    @Getter @Setter
    private Book bookToCreate = new Book();

    @Getter
    private List<Book> authorBooks;

    @Getter
    private Author author;

    @Getter
    private List<Genre> allGenres;

    @Getter @Setter
    private Long[] selectedGenreIds;

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String authorIdParam = params.get("authorId");
        if (authorIdParam != null && !authorIdParam.isEmpty()) {
            this.authorId = Long.parseLong(authorIdParam);
            this.author = authorMapper.selectByPrimaryKey(authorId);
            loadAuthorBooks();
        }

        String bookIdParam = params.get("bookId");
        if (bookIdParam != null && !bookIdParam.isEmpty()) {
            this.bookId = Long.parseLong(bookIdParam);
            Book book = bookMapper.selectByPrimaryKey(bookId);
            if (book != null) {
                this.bookToCreate = book;

                // Load selected genres
                List<Genre> bookGenres = genreMapper.selectByBookId(bookId);
                if (bookGenres != null && !bookGenres.isEmpty()) {
                    selectedGenreIds = new Long[bookGenres.size()];
                    for (int i = 0; i < bookGenres.size(); i++) {
                        selectedGenreIds[i] = bookGenres.get(i).getId();
                    }
                }
            }
        }

        this.allGenres = genreMapper.selectAll();
    }

    private void loadAuthorBooks() {
        if (authorId != null) {
            authorBooks = bookMapper.selectByAuthorId(authorId);
        }
    }

    public String prepareNewBook() {
        bookToCreate = new Book();
        bookToCreate.setAuthorId(authorId);
        selectedGenreIds = new Long[0];
        return "/mybatis/book_form?faces-redirect=true&authorId=" + authorId;
    }

    @Transactional
    public String saveBook() {
        bookToCreate.setAuthorId(authorId);

        if (bookToCreate.getId() == null) {
            bookMapper.insert(bookToCreate);
        } else {
            bookMapper.updateByPrimaryKey(bookToCreate);

            bookMapper.deleteBookGenres(bookToCreate.getId());
        }

        if (selectedGenreIds != null && selectedGenreIds.length > 0) {
            for (Long genreId : selectedGenreIds) {
                bookMapper.insertBookGenre(bookToCreate.getId(), genreId);
            }
        }

        return "/mybatis/author_books?faces-redirect=true&authorId=" + authorId;
    }

    public String getAuthorName() {
        if (author != null) {
            return author.getFirstName() + " " + author.getLastName();
        }
        return "";
    }
}