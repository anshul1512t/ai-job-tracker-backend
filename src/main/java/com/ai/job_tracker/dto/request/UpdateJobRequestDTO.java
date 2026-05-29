package com.ai.job_tracker.dto.request;


import com.ai.job_tracker.enums.ApplicationStatus;
import com.ai.job_tracker.enums.JobType;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UpdateJobRequestDTO {
    private ApplicationStatus status;
    @Positive
    private Long salaryExpectation;
    private String location;
    private JobType jobType;
    private String jobDescription;
    private String notes;
}
