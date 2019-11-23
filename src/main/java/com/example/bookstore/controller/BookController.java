package com.example.bookstore.controller;

import com.example.bookstore.model.Books;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;


    // Create
    @PostMapping("/create")
    public Books createBook(@Valid @RequestBody Books book) {
        System.out.println(book.getAuthor());
        return bookRepository.save(book);
        // return book;
    }

    // Get All Notes
    @GetMapping()
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get a Single Note
    @GetMapping("/{id}")
    public Books getBookById(@PathVariable(value = "id") Long bookId) {
        return bookRepository.findById(bookId)
        .orElseThrow();
    }

    // Update a Note
    @PutMapping("/update/{id}")
    public Books updateBook(@PathVariable(value = "id") Long bookId,
                                            @Valid @RequestBody Books bookDetails) {

        Books book = bookRepository.findById(bookId)
                .orElseThrow();
        book.setTitle(bookDetails.getTitle());
        book.setDescription(bookDetails.getDescription());
        book.setReleaseDate(bookDetails.getReleaseDate());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(bookDetails.getCategory());
        book.setPublisher(bookDetails.getPublisher());

        Books updatedBook = bookRepository.save(book);
        return updatedBook;
    }

    // Delete a Note
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long bookId) {
        Books book = bookRepository.findById(bookId)
                .orElseThrow();

        bookRepository.delete(book);

        return ResponseEntity.ok().build();
    }

}