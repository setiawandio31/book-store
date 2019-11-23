package com.example.bookstore.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.bookstore.model.Books;
import com.example.bookstore.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateBookPriceController {
    @Autowired
    BookRepository bookRepository;

    // Calculate Basic Price
    public double basicPrice(Long id) {
        Books book = bookRepository.findById(id).orElseThrow();
        final double ratePrice1 = 1.5;
        final double ratePrice2 = 1.3;
        Date date = new Date();
        double basicPrice;

        double baseProduction = book.getPublisher().getGrade().getBestProduction();
        int yearNow = date.getYear();
        int yearReleaseDate = book.getReleaseDate().getYear();

        if ( yearReleaseDate == yearNow ) {
            basicPrice = baseProduction * ratePrice1;
        } else {
            basicPrice = baseProduction * ratePrice2;
        }

        return basicPrice;
    }

    // Calculate Tax
    public double tax(Long id) {
        Books book = bookRepository.findById(id).orElseThrow();
        double tax;
        final double taxRate1 = 0.05;
        final double taxRate2 = 0.1; 
        
        double baseProduction = book.getPublisher().getGrade().getBestProduction();
        String publisherCountry = book.getPublisher().getCountry();

        if ( publisherCountry.equalsIgnoreCase("Indonesia") ) {
            tax = baseProduction * taxRate1;
        } else {
            tax = baseProduction * taxRate2;
        }

        return tax;
    }

    // Update Book Price
    @PutMapping("/api/calculateprice")
    public HashMap<String, Object> updatePrice(){
        List<Books> listBook = bookRepository.findAll();
        double price;

        for ( Books book : listBook ) {
            price = basicPrice(book.getBookId()) + tax(book.getBookId());   
            
            book.setPrice(price);
            bookRepository.save(book);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("status", 200);
        result.put("message", "success");

        return result;
    }
}