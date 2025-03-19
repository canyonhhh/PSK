package com.example.lab1.ui;

import com.example.lab1.entities.Author;
import com.example.lab1.entities.Book;
import com.example.lab1.services.AuthorService;
import com.example.lab1.services.BookService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("booksController")
@SessionScoped
public class BooksController implements Serializable {
    @Inject
    private BookService bookService;

    @Inject
    private AuthorService authorService;

    // Getters and setters
    @Getter
    @Setter
    private Long authorId;
    @Getter
    @Setter
    private Book selectedBook = new Book();
    @Getter
    private List<Book> books = new ArrayList<>();

    public void loadAuthorBooks() {
        if (authorId != null) {
            Author author = authorService.getAuthorById(authorId);
            if (author != null && author.getBooks() != null) {
                books = author.getBooks();
            }
        }
    }

    public void prepareNewBook() {
        selectedBook = new Book();
        if (authorId != null) {
            Author author = authorService.getAuthorById(authorId);
            selectedBook.setAuthor(author);
        }
    }

    public void selectBook(Book book) {
        selectedBook = book;
    }

    public String saveBook() {
        if (selectedBook.getId() == null) {
            bookService.createBook(selectedBook);
        } else {
            bookService.updateBook(selectedBook);
        }
        loadAuthorBooks();
        return "author_books.xhtml?faces-redirect=true&authorId=" + authorId;
    }

    public String viewAuthorBooks(Long authorId) {
        this.authorId = authorId;
        return "author_books.xhtml?faces-redirect=true&amp;authorId=" + authorId;
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