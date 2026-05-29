package com.ai.job_tracker.service;

import org.springframework.stereotype.Service;

import com.ai.job_tracker.dto.response.DashboardSummaryDTO;
import com.ai.job_tracker.enums.ApplicationStatus;

import com.ai.job_tracker.repository.CoverLetterRepository;
import com.ai.job_tracker.repository.JobApplicationRepository;
import com.ai.job_tracker.repository.ResumeAnalysisRepository;
import com.ai.job_tracker.repository.ResumeRepository;
import com.ai.job_tracker.repository.SkillGapAnalysisRepository;
import com.ai.job_tracker.utils.HelperFX;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final JobApplicationRepository jobApplicationRepository;
    private final ResumeRepository resumeRepository;
    private final ResumeAnalysisRepository resumeAnalysisRepository;
    private final CoverLetterRepository coverLetterRepository;
    private final SkillGapAnalysisRepository skillGapAnalysisRepository;

    public DashboardSummaryDTO getSummary() {

    Long userId = HelperFX.getCurrentUser().getId();

    Double averageMatch =
            skillGapAnalysisRepository.findAverageMatchPercentageByUserId(userId);

    return DashboardSummaryDTO.builder()
            .totalApplications(jobApplicationRepository.countByUserId(userId))
            .appliedCount(jobApplicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.APPLIED))
            .interviewCount(jobApplicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.INTERVIEW))
            .rejectedCount(jobApplicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.REJECTED))
            .offerCount(jobApplicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.OFFER))

            .totalResumes(resumeRepository.countByUserId(userId))
            .totalAiFeedbacks(resumeAnalysisRepository.countByUserId(userId))
            .totalCoverLetters(coverLetterRepository.countByUserId(userId))
            .totalSkillGapReports(skillGapAnalysisRepository.countByUserId(userId))

            .averageMatchPercentage(averageMatch)
            .build();
}
}