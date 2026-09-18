package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> findAll() {
        return bookMapper.findAll();
    }
    @Override
    public Book findById(Integer id) {
        return bookMapper.findById(id);
    }
    @Override
    public int insert(Book book) {
        return bookMapper.insert(book);
    }
    @Override
    public int update(Book book) {
        return bookMapper.update(book);
    }
    @Override
    public int delete(Integer id) {
        return bookMapper.delete(id);
    }
}
