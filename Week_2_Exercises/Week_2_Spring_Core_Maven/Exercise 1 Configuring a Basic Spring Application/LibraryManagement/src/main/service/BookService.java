package com.library.service;

import com.library.repository.BookRepository;

public class BookService {

    private BookRepository bookRepository;

    // Setter method for dependency injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Method to manage books
    public void manageBooks() {
        System.out.println("Managing books in the library.");
        bookRepository.listBooks();
    }
}

