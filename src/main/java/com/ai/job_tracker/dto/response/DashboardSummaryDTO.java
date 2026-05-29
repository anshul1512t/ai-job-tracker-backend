package com.ai.job_tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryDTO {

    private Long totalApplications;
    private Long appliedCount;
    private Long interviewCount;
    private Long rejectedCount;
    private Long offerCount;

    private Long totalResumes;
    private Long totalAiFeedbacks;
    private Long totalCoverLetters;
    private Long totalSkillGapReports;

    private Double averageMatchPercentage;
}

