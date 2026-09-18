package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/api/books")
    public Result<List<Book>> findAll() {
        return Result.success(bookService.findAll());
    }
    @GetMapping("api/books/{id}")
    public Result<Book> findById(@PathVariable Integer id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return Result.error("书不存在，id=" + id);
        }
        return Result.success(book);
    }
    @PostMapping("api/books")
    public Result<Void> insert(@RequestBody Book book) {
        bookService.insert(book);
        return Result.success(null);
    }
    @PutMapping("api/books/{id}")
    public Result<Void> update(@RequestBody Book book, @PathVariable Integer id) {
        book.setId(id);
        bookService.update(book);
        return Result.success(null);
    }
    @DeleteMapping("api/books/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        bookService.delete(id);
        return Result.success(null);
    }
}
