package com.ai.job_tracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.job_tracker.ai.dto.CoverLetterResponseDTO;
import com.ai.job_tracker.service.CoverLetterService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/cover-letter")
public class CoverLetterController {

    @Autowired
    private CoverLetterService coverLetterService;

    @GetMapping
    public ResponseEntity<List<CoverLetterResponseDTO>> getCoverLetters() {
        return ResponseEntity.ok(coverLetterService.getCoverLetters());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoverLetterById(@PathVariable Long id) {

        coverLetterService.deleteCoverLetterById(id);

        return ResponseEntity.ok("Cover letter deleted successfully");
    }
}
