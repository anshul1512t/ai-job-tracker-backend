package com.ai.job_tracker.ai.dto;

import java.time.LocalDateTime;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeFeedbackResponseDTO {

    private Long resumeAnalysisId;

    private Double score;

    private List<String> strengths;

    private List<String> improvements;

    private String summary;

    private LocalDateTime createdAt;
}