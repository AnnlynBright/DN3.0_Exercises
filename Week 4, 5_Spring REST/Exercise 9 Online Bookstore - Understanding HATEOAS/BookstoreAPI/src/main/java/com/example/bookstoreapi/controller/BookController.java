package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.entity.Book;
import com.example.bookstoreapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/books")
public class BookController<EntityModel> {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<EntityModel<Book>> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(toModel(savedBook), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Book>> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> new ResponseEntity<>(toModel(book), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<Book>>> getAllBooks() {
        List<EntityModel<Book>> books = bookRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Book>> updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthor(bookDetails.getAuthor());
                    book.setPrice(bookDetails.getPrice());
                    book.setIsbn(bookDetails.getIsbn());
                    Book updatedBook = bookRepository.save(book);
                    return new ResponseEntity<>(toModel(updatedBook), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private EntityModel<Book> toModel(Book book) {
        EntityModel<Book> bookModel = EntityModel.of(book);

        // Add self link
        bookModel.add(linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel());

        // Add link to all books
        bookModel.add(linkTo(methodOn(BookController.class).getAllBooks()).withRel("books"));

        return bookModel;
    }
}
