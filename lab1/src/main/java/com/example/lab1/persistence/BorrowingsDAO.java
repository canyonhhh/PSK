package com.example.lab1.persistence;

import com.example.lab1.entities.Borrowing;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class BorrowingsDAO {
    @Inject
    private EntityManager em;

    public void persist(Borrowing borrowing){
        this.em.persist(borrowing);
    }

    public Borrowing findOne(Long id){
        return em.find(Borrowing.class, id);
    }

    public Borrowing update(Borrowing borrowing){
        return em.merge(borrowing);
    }
}