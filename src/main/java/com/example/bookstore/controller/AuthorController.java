package com.example.bookstore.controller;

import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;


    // Create
    @PostMapping("/create")
    public Author createAuthor(@Valid @RequestBody Author id) {
        return authorRepository.save(id);
    }

    // Get All Notes
    @GetMapping()
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Get a Single Note
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable(value = "id") Long authorId) {
        return authorRepository.findById(authorId).
        orElseThrow();
    }

    // Update 
    @PutMapping("/update/{id}")
    public Author updateAuthor(@PathVariable(value = "id") Long authorId,
                            @Valid @RequestBody Author authorDetails) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow();

        author.setFirstName(authorDetails.getFirstName());
        author.setLastName(authorDetails.getLastName());

        Author updatedAuthor = authorRepository.save(author);
        return updatedAuthor;
    }

    // Delete a Note
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable(value = "id") Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow();

        authorRepository.delete(author);

        return ResponseEntity.ok().build();
    }

}