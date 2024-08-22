package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.service.BookService;
import org.testng.annotations.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testGetAllBooks() throws Exception {
        // Arrange: Mock the service method
        Book book1 = new Book(1L, "Title1", "Author1", 29.99);
        Book book2 = new Book(2L, "Title2", "Author2", 39.99);
        List<Book> books = Arrays.asList(book1, book2);

        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        // Act & Assert: Perform the GET request and verify the results
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Title2"));
    }

    @Test
    public void testGetBookById() throws Exception {
        // Arrange: Mock the service method
        Book book = new Book(1L, "Title1", "Author1", 29.99);
        Mockito.when(bookService.getBookById(1L)).thenReturn(book);

        // Act & Assert: Perform the GET request and verify the results
        mockMvc.perform(MockMvcRequestBuilders.get("/books/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(29.99));
    }

    @Test
    public void testCreateBook() throws Exception {
        // Arrange: Mock the service method
        Book book = new Book(1L, "New Book", "New Author", 49.99);
        Mockito.when(bookService.saveBook(Mockito.any(Book.class))).thenReturn(book);

        // Act & Assert: Perform the POST request and verify the results
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Book\", \"author\": \"New Author\", \"price\": 49.99}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("New Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(49.99));
    }
}
