package com.example.lab1.ui;

import com.example.lab1.entities.Author;
import com.example.lab1.entities.Book;
import com.example.lab1.services.AuthorService;
import com.example.lab1.services.BookService;
import lombok.Getter;
import lombok.Setter;

import com.example.lab1.entities.Genre;
import com.example.lab1.services.GenreService;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class BooksController implements Serializable {
    @Inject
    private BookService bookService;

    @Inject
    private AuthorService authorService;

    @Inject
    private GenreService genreService;

    // Getters and setters
    @Getter
    @Setter
    private Long authorId;

    @Getter
    @Setter
    private Long bookId;

    @Getter
    @Setter
    private Book selectedBook = new Book();

    @Getter
    private List<Book> books = new ArrayList<>();

    @Getter
    private List<Genre> allGenres = new ArrayList<>();

    @Getter
    @Setter
    private Long[] selectedGenreIds;

    @PostConstruct
    public void init() {
        loadGenres();
    }

    private void loadGenres() {
        allGenres = genreService.getAllGenresWithBooks();
    }
    public void loadAuthorBooks() {
        if (authorId != null) {
            Author author = authorService.getAuthorById(authorId);
            if (author != null && author.getBooks() != null) {
                books = author.getBooks();
            }
        }

        // Check if we need to load a specific book for editing
        if (bookId != null) {
            selectedBook = bookService.getBookById(bookId);

            // Pre-select the genres for this book
            if (selectedBook != null && selectedBook.getGenres() != null) {
                selectedGenreIds = selectedBook.getGenres().stream()
                        .map(Genre::getId)
                        .toArray(Long[]::new);
            }
        }
    }

    public String prepareNewBook() {
        selectedBook = new Book();
        selectedGenreIds = new Long[0]; // Reset genre selection
        if (authorId != null) {
            Author author = authorService.getAuthorById(authorId);
            selectedBook.setAuthor(author);
        }
        return "book_form?faces-redirect=true&authorId=" + authorId;
    }

    public String selectBook(Book book) {
        selectedBook = book;
        return "book_form?faces-redirect=true&authorId=" + authorId + "&bookId=" + book.getId();
    }

    public String saveBook() {
        // Handle genres selection
        if (selectedGenreIds != null && selectedGenreIds.length > 0) {
            List<Genre> selectedGenres = Arrays.stream(selectedGenreIds)
                    .map(genreId -> genreService.getGenreById(genreId))
                    .collect(Collectors.toList());
            selectedBook.setGenres(selectedGenres);
        } else {
            selectedBook.setGenres(new ArrayList<>());
        }

        if (selectedBook.getId() == null) {
            bookService.createBook(selectedBook);
        } else {
            bookService.updateBook(selectedBook);
        }
        loadAuthorBooks();
        return "author_books?faces-redirect=true&authorId=" + authorId;
    }

    public String viewAuthorBooks(Long authorId) {
        this.authorId = authorId;
        return "author_books?faces-redirect=true&authorId=" + authorId;
    }

    public String getAuthorName() {
        if (authorId != null) {
            Author author = authorService.getAuthorById(authorId);
            if (author != null) {
                return author.getFirstName() + " " + author.getLastName();
            }
        }
        return "";
    }
}