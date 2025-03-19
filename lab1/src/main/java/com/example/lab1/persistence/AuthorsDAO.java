package com.example.lab1.persistence;

import com.example.lab1.entities.Author;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class AuthorsDAO {
    @Inject
    private EntityManager em;

    public void persist(Author author){
        this.em.persist(author);
    }

    public List<Author> loadAll() {
        return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    public Author findOne(Long id){
        return em.find(Author.class, id);
    }

    public Author update(Author author){
        return em.merge(author);
    }
}