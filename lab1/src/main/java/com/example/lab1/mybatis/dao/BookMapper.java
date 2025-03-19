package com.example.lab1.mybatis.dao;

import com.example.lab1.mybatis.model.Book;
import org.mybatis.cdi.Mapper;
import java.util.List;

@Mapper
public interface BookMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Book record);
    Book selectByPrimaryKey(Long id);
    List<Book> selectAll();
    List<Book> selectByAuthorId(Long authorId);
    int updateByPrimaryKey(Book record);
    List<Book> selectByGenreId(Long genreId);
    void insertBookGenre(Long bookId, Long genreId);
    void deleteBookGenres(Long bookId);
}