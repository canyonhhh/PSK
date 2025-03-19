package com.example.lab1.services;

import com.example.lab1.entities.Book;
import com.example.lab1.entities.Borrowing;
import com.example.lab1.entities.Member;
import com.example.lab1.persistence.BooksDAO;
import com.example.lab1.persistence.BorrowingsDAO;
import com.example.lab1.persistence.MembersDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;

@RequestScoped
public class BorrowingService {
    @Inject
    private BorrowingsDAO borrowingsDAO;

    @Inject
    private BooksDAO booksDAO;

    @Inject
    private MembersDAO membersDAO;

    @Transactional
    public Borrowing borrowBook(Long memberId, Long bookId, int daysToReturn) {
        Member member = membersDAO.findOne(memberId);
        Book book = booksDAO.findOne(bookId);

        if (member == null || book == null) {
            throw new IllegalArgumentException("Member or book not found");
        }

        if (book.getCopies() <= 0) {
            throw new IllegalStateException("No copies available for borrowing");
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setMember(member);
        borrowing.setBook(book);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusDays(daysToReturn));
        borrowing.setStatus(Borrowing.Status.BORROWED);

        book.setCopies(book.getCopies() - 1);

        booksDAO.update(book);
        borrowingsDAO.persist(borrowing);

        return borrowing;
    }

    @Transactional
    public void returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingsDAO.findOne(borrowingId);

        if (borrowing == null) {
            throw new IllegalArgumentException("Borrowing record not found");
        }

        if (borrowing.getStatus() != Borrowing.Status.BORROWED) {
            throw new IllegalStateException("Book is not currently borrowed");
        }

        borrowing.setReturnDate(LocalDate.now());
        borrowing.setStatus(Borrowing.Status.RETURNED);

        Book book = borrowing.getBook();
        book.setCopies(book.getCopies() + 1);

        booksDAO.update(book);
        borrowingsDAO.update(borrowing);
    }

    @Transactional
    public void markAsLost(Long borrowingId) {
        Borrowing borrowing = borrowingsDAO.findOne(borrowingId);

        if (borrowing == null) {
            throw new IllegalArgumentException("Borrowing record not found");
        }

        borrowing.setStatus(Borrowing.Status.LOST);
        borrowingsDAO.update(borrowing);
    }
}