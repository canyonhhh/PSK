package com.example.lab1.ui;

import com.example.lab1.entities.Book;
import com.example.lab1.services.BookService;
import com.example.lab1.services.OptimisticLockService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class OptimisticLockController implements Serializable {

    @Inject
    private BookService bookService;

    @Inject
    private OptimisticLockService optLockService;

    @Getter @Setter
    private Long selectedBookId;

    @Getter @Setter
    private Integer newCopiesValue;

    // This will hold our stale reference
    private Book staleBookReference;

    @Getter
    private List<Book> availableBooks;

    @Getter
    private boolean step1Complete = false;

    @Getter
    private boolean step2Complete = false;

    @Getter
    private boolean step3Failed = false;

    public void loadBooks() {
        availableBooks = bookService.getAllBooks();

        // Reset the state
        step1Complete = false;
        step2Complete = false;
        step3Failed = false;
        staleBookReference = null;
    }

    public void step1GetReference() {
        if (selectedBookId != null) {
            staleBookReference = optLockService.getBookReference(selectedBookId);
            if (staleBookReference != null) {
                newCopiesValue = staleBookReference.getCopies();
                step1Complete = true;

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Step 1 Complete",
                                "Got a reference to book: " + staleBookReference.getTitle() +
                                        " with version: " + staleBookReference.getVersion()));
            }
        }
    }

    public void step2SimulateOtherUser() {
        if (staleBookReference != null) {
            optLockService.updateBookAsAnotherUser(staleBookReference.getId());
            step2Complete = true;

            // Get fresh reference to show updated values
            Book updatedBook = bookService.getBookById(staleBookReference.getId());

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Step 2 Complete",
                            "Another user updated the book. New copies value: " + updatedBook.getCopies() +
                                    ", new version: " + updatedBook.getVersion()));
        }
    }

    public void step3UpdateWithStaleReference() {
        if (staleBookReference != null && newCopiesValue != null) {
            boolean updateSucceeded = optLockService.updateBookWithStaleReference(staleBookReference, newCopiesValue);

            if (updateSucceeded) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Unexpected Result",
                                "No OptimisticLockException was thrown. This is unexpected!"));
            } else {
                // expected
                step3Failed = true;

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Step 3 Complete",
                                "OptimisticLockException thrown as expected! The stale reference with version " +
                                        staleBookReference.getVersion() + " could not be used to update the book."));

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Transaction Status",
                                "The current transaction has been marked for rollback. " +
                                        "Any other operations in this transaction will fail."));
            }
        }
    }

    public void step4UpdateWithFreshReference() {
        if (selectedBookId != null && newCopiesValue != null) {
            optLockService.updateBookWithFreshReference(selectedBookId, newCopiesValue);

            // Get fresh reference to show updated values
            Book updatedBook = bookService.getBookById(selectedBookId);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Step 4 Complete",
                            "Successfully updated book with fresh reference. " +
                                    "New copies value: " + updatedBook.getCopies() +
                                    ", new version: " + updatedBook.getVersion()));

            // Reset to start over
            step1Complete = false;
            step2Complete = false;
            step3Failed = false;
            staleBookReference = null;
        }
    }

    public Book getSelectedBook() {
        if (selectedBookId != null) {
            return bookService.getBookById(selectedBookId);
        }
        return null;
    }
}