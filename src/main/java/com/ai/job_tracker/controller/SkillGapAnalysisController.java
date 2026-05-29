package com.ai.job_tracker.controller;

import com.ai.job_tracker.ai.dto.SkillGapResponseDTO;
import com.ai.job_tracker.service.SkillGapAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skill-gap-analysis")
@RequiredArgsConstructor
public class SkillGapAnalysisController {

    private final SkillGapAnalysisService service;

    @GetMapping
    public ResponseEntity<List<SkillGapResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllAnalyses());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        service.deleteById(id);

        return ResponseEntity.ok("Skill gap analysis deleted successfully");
    }
}