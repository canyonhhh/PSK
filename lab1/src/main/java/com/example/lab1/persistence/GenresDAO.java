package com.example.lab1.persistence;

import com.example.lab1.entities.Genre;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class GenresDAO {
    @Inject
    private EntityManager em;

    public void persist(Genre genre){
        this.em.persist(genre);
    }

    public Genre findOne(Long id){
        return em.find(Genre.class, id);
    }

    public Genre update(Genre genre){
        return em.merge(genre);
    }

    public List<Genre> findAllWithBooks() {
        return em.createQuery("SELECT g FROM Genre g LEFT JOIN FETCH g.books ORDER BY g.name", Genre.class)
                .getResultList();
    }
}