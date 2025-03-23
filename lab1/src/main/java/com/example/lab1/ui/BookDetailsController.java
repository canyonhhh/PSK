package com.example.lab1.ui;

import com.example.lab1.entities.Book;
import com.example.lab1.entities.Genre;
import com.example.lab1.services.BookService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class BookDetailsController implements Serializable {
    @Inject
    private BookService bookService;

    @Getter
    @Setter
    private Long bookId;

    @Getter
    private Book book;

    public void loadBook() {
        if (bookId != null) {
            book = bookService.getBookById(bookId);
        }
    }

    public String getAuthorName() {
        if (book != null && book.getAuthor() != null) {
            return book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName();
        }
        return "No author assigned";
    }
}