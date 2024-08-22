package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.model.Book;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // Sample list of books
    private List<Book> books = Arrays.asList(
            new Book(1L, "Effective Java", "Joshua Bloch"),
            new Book(2L, "Clean Code", "Robert C. Martin")
    );

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Book> getBooks() {
        return books;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Book createBook(@RequestBody Book book) {
        // In a real application, you would save the book to a database here
        return book;
    }
}
