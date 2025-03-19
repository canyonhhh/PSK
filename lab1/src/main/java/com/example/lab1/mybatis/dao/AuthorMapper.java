package com.example.lab1.mybatis.dao;

import com.example.lab1.mybatis.model.Author;
import org.mybatis.cdi.Mapper;
import java.util.List;

@Mapper
public interface AuthorMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Author record);
    Author selectByPrimaryKey(Long id);
    List<Author> selectAll();
    int updateByPrimaryKey(Author record);
}