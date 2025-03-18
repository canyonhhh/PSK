package com.example.lab1.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    @Basic
    private String firstName;

    @Basic
    private String lastName;

    @Basic
    private LocalDate birthday;

    @OneToMany(mappedBy = "author")
    private List<Book> books;
}
