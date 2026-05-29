package com.ai.job_tracker.dto.request;

import com.ai.job_tracker.enums.ApplicationStatus;
import com.ai.job_tracker.enums.JobType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddJobApplicationRequestDTO {

    @NotBlank
    private String companyName;

    @NotBlank
    private String jobTitle;

    @NotNull
    private ApplicationStatus status;

    @NotNull
    private JobType jobType;

    @NotBlank
    private String location;

    @NotNull
    @Positive
    private Long salaryExpectation;

    @NotNull
    private LocalDate applicationDate;

    @NotBlank
    private String jobUrl;

    @NotBlank
    private String jobDescription;

    private String notes;
}