package com.example.lab1.mybatis.dao;

import com.example.lab1.mybatis.model.Genre;
import org.mybatis.cdi.Mapper;
import java.util.List;

@Mapper
public interface GenreMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Genre record);
    Genre selectByPrimaryKey(Long id);
    List<Genre> selectAll();
    List<Genre> selectByBookId(Long bookId);
    int updateByPrimaryKey(Genre record);
}