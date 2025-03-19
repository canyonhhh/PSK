package com.example.lab1.mybatis.model;

public class Book {
    private Long id;
    private String isbn;
    private String title;
    private Integer copies;
    private Long authorId;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getCopies() { return copies; }
    public void setCopies(Integer copies) { this.copies = copies; }
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
}