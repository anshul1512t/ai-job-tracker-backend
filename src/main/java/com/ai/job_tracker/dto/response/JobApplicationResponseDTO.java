package com.ai.job_tracker.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ai.job_tracker.enums.ApplicationStatus;
import com.ai.job_tracker.enums.JobType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationResponseDTO {

    private Long id;

    private String companyName;

    private String jobTitle;

    private ApplicationStatus applicationStatus;

    private JobType jobType;

    private String location;

    private Long salaryExceptation;

    private LocalDate applicationDate;

    private String jobUrl;

    private String jobDescription;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}