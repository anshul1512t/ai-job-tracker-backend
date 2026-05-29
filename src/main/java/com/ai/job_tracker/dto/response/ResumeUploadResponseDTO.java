package com.ai.job_tracker.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ResumeUploadResponseDTO {
    private long id;
    private String filename;
    private LocalDateTime uploadTime;
}
