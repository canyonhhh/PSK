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
    private String isbn;

    @Basic
    private String title;

    @Basic
    private Integer copies;

    @Version
    private Long version;

    @ManyToOne
    private Author author;

    @ManyToMany
    private List<Genre> genres;
}