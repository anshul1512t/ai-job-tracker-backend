package com.ai.job_tracker.service;

import com.ai.job_tracker.ai.dto.SkillGapResponseDTO;
import com.ai.job_tracker.model.SkillGapAnalysis;
import com.ai.job_tracker.repository.SkillGapAnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillGapAnalysisService {

    private final SkillGapAnalysisRepository repository;

    public List<SkillGapResponseDTO> getAllAnalyses() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private SkillGapResponseDTO mapToDTO(SkillGapAnalysis entity) {

        return new SkillGapResponseDTO(
                entity.getId(),
                entity.getMatchPercentage(),
                convertStringToList(entity.getMissingSkills()),
                convertStringToList(entity.getRecommendedSkills()),
                entity.getAnalysisText(),
                entity.getJobApplication() != null
                        ? entity.getJobApplication().getId()
                        : null
        );
    }

    private List<String> convertStringToList(String value) {

        if (value == null || value.isBlank()) {
            return Collections.emptyList();
        }

        return Arrays.stream(value.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}