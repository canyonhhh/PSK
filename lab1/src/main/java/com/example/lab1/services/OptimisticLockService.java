package com.example.lab1.services;

import com.example.lab1.entities.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

@Stateless
public class OptimisticLockService {
    @Inject
    private EntityManager em;

    public Book getBookReference(Long bookId) {
        return em.find(Book.class, bookId);
    }

    @Transactional
    public void updateBookAsAnotherUser(Long bookId) {
        Book book = em.find(Book.class, bookId);
        book.setCopies(book.getCopies() + 5);
        em.merge(book);
    }

    @Transactional
    public boolean updateBookWithStaleReference(Book staleBook, int newCopies) {
        try {
            staleBook.setCopies(newCopies);
            em.merge(staleBook);
            em.flush();
            return true;
        } catch (OptimisticLockException e) {
            return false;
        }
    }

    @Transactional
    public void updateBookWithFreshReference(Long bookId, int newCopies) {
        Book freshBook = em.find(Book.class, bookId);
        freshBook.setCopies(newCopies);
        em.merge(freshBook);
    }
}