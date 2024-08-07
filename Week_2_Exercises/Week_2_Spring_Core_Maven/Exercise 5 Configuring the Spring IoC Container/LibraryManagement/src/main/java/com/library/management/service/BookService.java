package com.library.management.service;

import com.library.management.repository.BookRepository;

public class BookService {

    private BookRepository bookRepository;

    // Setter for BookRepository
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Method for managing books
    public void manageBooks() {
        System.out.println("Managing books using " + bookRepository);
        bookRepository.accessBooks();
    }
}

