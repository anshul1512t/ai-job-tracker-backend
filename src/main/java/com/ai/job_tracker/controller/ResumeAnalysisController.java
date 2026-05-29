package com.ai.job_tracker.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.job_tracker.ai.dto.ResumeFeedbackResponseDTO;
import com.ai.job_tracker.ai.service.AiService;

@RestController
@RequestMapping("/api/resume-analysis")
public class ResumeAnalysisController {
    
    @Autowired
    private AiService aiService;
    
        @GetMapping("/feedbacks")
        public ResponseEntity<List<ResumeFeedbackResponseDTO>> getMyFeedbacks() {

                return ResponseEntity.ok(
                                aiService.getUserFeedbacks());
        }

        @GetMapping("/feedbacks/{id}")
        public ResponseEntity<ResumeFeedbackResponseDTO> getFeedbackById(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                aiService.getFeedbackById(id));
        }

        @DeleteMapping("/feedbacks/{id}")
        public ResponseEntity<String> deleteFeedback(
                        @PathVariable Long id) {

                aiService.deleteFeedback(id);

                return ResponseEntity.ok("Feedback deleted");
        }
}
