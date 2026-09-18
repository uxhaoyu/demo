package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;   // 操作 Redis 的工具

    @Autowired
    private ObjectMapper objectMapper;           // Jackson：对象 ↔ JSON

    private static final String CACHE_KEY = "books:list";   // 缓存用的 key

    @Override
    public List<Book> findAll() {
        // 1. 先查 Redis 缓存
        String json = redisTemplate.opsForValue().get(CACHE_KEY);
        if (json != null) {
            System.out.println(">>> 从 Redis 缓存拿数据");
            try {
                return objectMapper.readValue(json, new TypeReference<List<Book>>() {});
            } catch (Exception e) {
                redisTemplate.delete(CACHE_KEY);   // 缓存坏了，删掉重新查
            }
        }
        // 2. 缓存没有 → 查 MySQL
        System.out.println(">>> 从 MySQL 查数据");
        List<Book> books = bookMapper.findAll();
        // 3. 查完写回缓存（对象 → JSON 字符串）
        try {
            redisTemplate.opsForValue().set(CACHE_KEY, objectMapper.writeValueAsString(books));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book findById(Integer id) {
        return bookMapper.findById(id);
    }

    @Override
    public int insert(Book book) {
        int rows = bookMapper.insert(book);
        redisTemplate.delete(CACHE_KEY);   // ⚠️ 数据变了，清缓存
        return rows;
    }

    @Override
    public int update(Book book) {
        int rows = bookMapper.update(book);
        redisTemplate.delete(CACHE_KEY);   // ⚠️ 数据变了，清缓存
        return rows;
    }

    @Override
    public int delete(Integer id) {
        int rows = bookMapper.delete(id);
        redisTemplate.delete(CACHE_KEY);   // ⚠️ 数据变了，清缓存
        return rows;
    }
}
