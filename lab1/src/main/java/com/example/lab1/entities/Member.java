package com.example.lab1.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
public class Member {
    @GeneratedValue
    @Id
    private Long id;

    @Basic
    private String firstName;

    @Basic
    private String lastName;

    @Basic
    private String email;

    @Basic
    private LocalDate joinDate;
    @OneToMany(mappedBy = "member")
    private List<Borrowing> borrowings;
}
