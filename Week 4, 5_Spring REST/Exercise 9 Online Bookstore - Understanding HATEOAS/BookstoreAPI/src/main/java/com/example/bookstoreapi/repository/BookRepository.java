package com.example.bookstoreapi.repository;

import com.example.bookstoreapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

