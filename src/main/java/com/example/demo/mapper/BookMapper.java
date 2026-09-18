package com.example.demo.mapper;

import com.example.demo.entity.Book;

import java.util.List;

public interface BookMapper {
    List<Book> findAll();
    Book findById(Integer id);
    int insert(Book book);
    int update(Book book);
    int delete(Integer id);
}
