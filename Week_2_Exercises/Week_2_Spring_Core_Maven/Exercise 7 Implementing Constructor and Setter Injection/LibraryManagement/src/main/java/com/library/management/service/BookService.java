package com.library.management.service;

import com.library.management.repository.BookRepository;

public class BookService {

    private BookRepository bookRepository;

    // Constructor-based injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Setter-based injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void manageBooks() {
        System.out.println("Managing books using " + bookRepository);
        bookRepository.accessBooks();
    }
}