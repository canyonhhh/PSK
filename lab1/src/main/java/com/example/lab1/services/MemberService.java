package com.example.lab1.services;

import com.example.lab1.entities.Member;
import com.example.lab1.persistence.MembersDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;

@RequestScoped
public class MemberService {
    @Inject
    private MembersDAO membersDAO;

    @Transactional
    public void registerMember(Member member) {
        if (member.getJoinDate() == null) {
            member.setJoinDate(LocalDate.now());
        }
        membersDAO.persist(member);
    }

    public Member getMemberById(Long id) {
        return membersDAO.findOne(id);
    }

    @Transactional
    public Member updateMember(Member member) {
        return membersDAO.update(member);
    }
}