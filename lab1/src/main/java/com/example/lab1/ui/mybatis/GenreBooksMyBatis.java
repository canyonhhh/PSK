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
public class GenreBooksMyBatis {
    @Inject
    private GenreMapper genreMapper;

    @Inject
    private BookMapper bookMapper;

    @Inject
    private AuthorMapper authorMapper;

    @Getter @Setter
    private Long genreId;

    @Getter
    private Genre genre;

    @Getter
    private List<BookWithAuthor> genreBooks = new ArrayList<>();

    // Helper class to hold book and author data together
    @Getter @Setter
    public static class BookWithAuthor {
        private Long id;
        private String isbn;
        private String title;
        private Integer copies;
        private String authorName;
        private Long authorId;

        public BookWithAuthor(Book book, Author author) {
            this.id = book.getId();
            this.isbn = book.getIsbn();
            this.title = book.getTitle();
            this.copies = book.getCopies();
            this.authorId = author.getId();
            this.authorName = author.getFirstName() + " " + author.getLastName();
        }
    }

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String genreIdParam = params.get("genreId");
        if (genreIdParam != null && !genreIdParam.isEmpty()) {
            this.genreId = Long.parseLong(genreIdParam);
            loadGenreBooks();
        }
    }

    private void loadGenreBooks() {
        if (genreId != null) {
            // Load genre details
            genre = genreMapper.selectByPrimaryKey(genreId);

            // Get all books for this genre
            List<Book> books = bookMapper.selectByGenreId(genreId);

            // Load author information for each book
            for (Book book : books) {
                Author author = authorMapper.selectByPrimaryKey(book.getAuthorId());
                genreBooks.add(new BookWithAuthor(book, author));
            }
        }
    }

    public String getGenreName() {
        return genre != null ? genre.getName() : "";
    }
}