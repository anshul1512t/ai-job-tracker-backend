package com.ai.job_tracker.ai.dto;

import java.time.LocalDateTime;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoverLetterResponseDTO {
    private Long id;
    private String coverLetter;
    private LocalDateTime createdAt;
    private Long jobApplicationId;

}
