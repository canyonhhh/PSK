package com.example.lab1.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class Genre {
    @Id
    @GeneratedValue
    private Long id;

    @Basic
    private String name;

    @Basic
    private String description;

    @ManyToMany(mappedBy = "genres")
    private List<Book> books;
}
