package com.example.demo.service;

import com.example.demo.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Integer id);
    int insert(Book book);
    int update(Book book);
    int delete(Integer id);
}
