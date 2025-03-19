package com.example.lab1.persistence;

import com.example.lab1.entities.Member;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class MembersDAO {
    @Inject
    private EntityManager em;

    public void persist(Member member){
        this.em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public Member update(Member member){
        return em.merge(member);
    }
}