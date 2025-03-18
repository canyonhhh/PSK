package com.example.lab1.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Borrowing {
    @GeneratedValue
    @Id
    private Long id;

    @Basic
    private LocalDate borrowDate;
    @Basic
    private LocalDate dueDate;
    @Basic
    private LocalDate returnDate;

    public enum Status {
        BORROWED, RETURNED, OVERDUE, LOST
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Member member;
    @ManyToOne
    private Book book;
}
