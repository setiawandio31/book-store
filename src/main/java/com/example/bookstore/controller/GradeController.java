package com.example.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.bookstore.model.Grade;
import com.example.bookstore.repository.GradeRepository;

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
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    GradeRepository gradeRepository;

    // Create
    @PostMapping("/create")
    public Grade createGrade(@Valid @RequestBody Grade grade) {
        return gradeRepository.save(grade);
    }

    // Get All
    @GetMapping
    public List<Grade> getAllGrade() {
        return gradeRepository.findAll();
    }

    // Get a Single Note
    @GetMapping("/{id}")
    public Grade getGradeById(@PathVariable(value = "id") Long gradeId) {
        return gradeRepository.findById(gradeId)
        .orElseThrow();
    }

    // Update a Note
    @PutMapping("/update/{id}")
    public Grade updateGrade(@PathVariable(value = "id") Long gradeId,
                                            @Valid @RequestBody Grade gradeDetails) {

        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow();
        grade.setQuality(gradeDetails.getQuality());
        grade.setBestProduction(gradeDetails.getBestProduction());

        Grade updateGrade = gradeRepository.save(grade);
        return updateGrade;
    }

    // Delete a Note
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGrade(@PathVariable(value = "id") Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow();

        gradeRepository.delete(grade);

        return ResponseEntity.ok().build();
    }
}