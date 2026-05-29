package com.ai.job_tracker.ai.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillGapResponseDTO {

    private Long analysisId;

    private Double matchPercentage;

    private List<String> missingSkills;

    private List<String> recommendedSkills;

    private String analysisText;

    private Long jobApplicationId;
}
