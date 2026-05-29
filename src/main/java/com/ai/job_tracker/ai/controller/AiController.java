package com.ai.job_tracker.ai.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ai.job_tracker.ai.dto.CoverLetterResponseDTO;
import com.ai.job_tracker.ai.dto.CoverletterRequestDTO;
import com.ai.job_tracker.ai.dto.ResumeFeedbackRequestDTO;
import com.ai.job_tracker.ai.dto.ResumeFeedbackResponseDTO;
import com.ai.job_tracker.ai.dto.SkillGapResponseDTO;
import com.ai.job_tracker.ai.service.AiService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ai")
public class AiController {

        @Autowired
        private AiService aiService;

        @GetMapping("/test")
        public String aiControllerTesting() {
                return aiService.testingAi();
        }

        @PostMapping("/resume-feedback")
        public ResponseEntity<ResumeFeedbackResponseDTO> resumeFeedback(
                        @Valid @RequestBody ResumeFeedbackRequestDTO request)
                        throws JsonMappingException, JsonProcessingException {

                return ResponseEntity.ok(
                                aiService.resumeFeedback(request));
        }

        @PostMapping("/cover-letter")
        public ResponseEntity<CoverLetterResponseDTO> coverLetterGenerator(
                        @Valid @RequestBody CoverletterRequestDTO request) {

                return ResponseEntity.ok(
                                aiService.coverLetterGeneration(request));
        }

        @PostMapping("/skill-gap")
        public ResponseEntity<SkillGapResponseDTO> skillGapAnalysis(
                        @Valid @RequestBody CoverletterRequestDTO request) {

                return ResponseEntity.ok(
                                aiService.skillGapAnalysis(request));
        }

}