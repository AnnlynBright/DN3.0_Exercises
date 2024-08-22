package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.entity.Book;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> books = new ArrayList<>();


    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = books.stream().filter(b -> b.getId().equals(id)).findFirst();
        if (((Optional<?>) book).isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "Book-Fetched-Successfully");
            return new ResponseEntity<>(book.get(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // POST method to add a new book
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) {
        book.setId((long) (books.size() + 1));
        books.add(book);
        return book;
    }



    // GET method to fetch all books
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks() {
        return books;
    }
}
