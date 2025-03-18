package com.example.lab1.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    @Basic
    private String author;

    @Basic
    private String isbn;

    @Basic
    private String title;

    @Basic
    private Integer copies;

    @ManyToOne
    private Author authorId;
    @ManyToMany
    private List<Genre> genres;
}
