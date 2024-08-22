package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.entity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    // Temporary in-memory store for books
    private List<Book> books = new ArrayList<>();

    // Initialize with some sample data
    public BookController() {
        books.add(new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", 10.99, "9780743273565"));
        books.add(new Book(2L, "1984", "George Orwell", 8.99, "9780451524935"));
    }

    // GET method to fetch all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(books, HttpStatus.OK);
    }




    // POST method to create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        book.setId((long) (books.size() + 1)); // Simple id assignment logic
        books.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // PUT method to update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Optional<Book> bookOptional = books.stream().filter(b -> b.getId().equals(id)).findFirst();
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPrice(updatedBook.getPrice());
            book.setIsbn(updatedBook.getIsbn());
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE method to delete a book by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> bookOptional = books.stream().filter(b -> b.getId().equals(id)).findFirst();
        if (bookOptional.isPresent()) {
            books.remove(bookOptional.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET method to fetch a book by id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = books.stream().filter(b -> b.getId().equals(id)).findFirst();
        if (book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET method to fetch books by title and/or author using query parameters
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {

        // Filter books based on query parameters
        List<Book> filteredBooks = books.stream()
                .filter(book -> (title == null || book.getTitle().equalsIgnoreCase(title)) &&
                        (author == null || book.getAuthor().equalsIgnoreCase(author)))
                .toList();

        // Return the filtered list or an empty list if no matches found
        return new ResponseEntity<>(filteredBooks, HttpStatus.OK);
    }


}
