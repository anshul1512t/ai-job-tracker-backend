package com.ai.job_tracker.ai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoverletterRequestDTO {
    @NotNull
    private Long resumeId;

    @NotNull
    private Long jobApplicationId;
}
