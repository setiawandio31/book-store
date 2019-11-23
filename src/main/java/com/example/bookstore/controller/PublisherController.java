package com.example.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.bookstore.model.Publisher;
import com.example.bookstore.repository.PublisherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {

    @Autowired
    PublisherRepository publisherRepository;

    // Create
    @PostMapping("/create")
    public Publisher createPublisher(@Valid @RequestBody Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    // Get All
    @GetMapping
    public List<Publisher> getAllPublisher() {
        return publisherRepository.findAll();
    }

    // Get a Single Note
    @GetMapping("/{id}")
    public Publisher getPublisherById(@PathVariable(value = "id") Long publisherId) {
        return publisherRepository.findById(publisherId)
        .orElseThrow();
    }

    // Update a Note
    @PutMapping("/update/{id}")
    public Publisher updatePublisher(@PathVariable(value = "id") Long publisherId,
                                            @Valid @RequestBody Publisher publisherDetails) {

        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow();
        publisher.setPublisherName(publisherDetails.getPublisherName());
        publisher.setCountry(publisherDetails.getCountry());

        Publisher updatedPublisher = publisherRepository.save(publisher);
        return updatedPublisher;
    }

    // Delete a Note
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable(value = "id") Long publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow();

        publisherRepository.delete(publisher);

        return ResponseEntity.ok().build();
    }
}