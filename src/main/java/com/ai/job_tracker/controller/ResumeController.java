package com.ai.job_tracker.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ai.job_tracker.dto.response.ResumeUploadResponseDTO;
import com.ai.job_tracker.service.ResumeService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
    @Autowired
    private ResumeService service;

    @PostMapping("upload")
    public ResponseEntity<ResumeUploadResponseDTO> uploadResume(@RequestParam("file") MultipartFile file)
            throws IOException {
        return ResponseEntity.ok(service.upload(file));
    }

    @GetMapping
    public ResponseEntity<List<ResumeUploadResponseDTO>> getAllResumes() {

        return ResponseEntity.ok(service.getAllResumes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(
            @PathVariable Long id) {

        service.deleteResume(id);

        return ResponseEntity.ok("Resume deleted");
    }

}