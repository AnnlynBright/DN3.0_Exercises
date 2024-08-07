package com.library.management.repository;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    public void accessBooks() {
        System.out.println("Accessing book data");
    }
}
