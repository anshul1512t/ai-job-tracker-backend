package com.ai.job_tracker.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ai.job_tracker.dto.request.AddJobApplicationRequestDTO;
import com.ai.job_tracker.dto.request.UpdateJobRequestDTO;
import com.ai.job_tracker.dto.response.JobApplicationResponseDTO;
import com.ai.job_tracker.enums.ApplicationStatus;
import com.ai.job_tracker.exception.JobApplicationIdNotExist;
import com.ai.job_tracker.model.JobApplication;
import com.ai.job_tracker.repository.JobApplicationRepository;
import com.ai.job_tracker.utils.HelperFX;

@Service
public class JobApplicationService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public JobApplicationResponseDTO createJob(AddJobApplicationRequestDTO request) {
        JobApplication jobApplication = modelMapper.map(request, JobApplication.class);
        jobApplication.setUser(HelperFX.getCurrentUser());
        return modelMapper.map(jobApplicationRepository.save(jobApplication), JobApplicationResponseDTO.class);
    }

    public Page<JobApplicationResponseDTO> getAlljobs(
            Pageable pageable,
            ApplicationStatus status,
            String keyword) {

        Long userId = HelperFX.getCurrentUser().getId();

        Page<JobApplication> jobs;

        if (status != null && StringUtils.hasText(keyword)) {
            jobs = jobApplicationRepository.searchByUserStatusAndKeyword(
                    userId,
                    status,
                    keyword,
                    pageable);

        } else if (status != null) {
            jobs = jobApplicationRepository.findByUserIdAndStatus(
                    userId,
                    status,
                    pageable);

        } else if (StringUtils.hasText(keyword)) {
            jobs = jobApplicationRepository.searchByUserAndKeyword(
                    userId,
                    keyword,
                    pageable);

        } else {
            jobs = jobApplicationRepository.findByUserId(userId, pageable);
        }

        return jobs.map(job -> modelMapper.map(job, JobApplicationResponseDTO.class));
    }

    public JobApplicationResponseDTO getJobById(Long jobId) {

        JobApplication jobApplication = jobApplicationRepository
                .findByIdAndUserId(jobId, HelperFX.getCurrentUser().getId())
                .orElseThrow(() -> new JobApplicationIdNotExist("job application id doesn't exist"));

        return modelMapper.map(jobApplication, JobApplicationResponseDTO.class);
    }

    public void deleteJob(Long id) {

        JobApplication job = jobApplicationRepository.findByIdAndUserId(id, HelperFX.getCurrentUser().getId())
                .orElseThrow(() -> new JobApplicationIdNotExist("Job application id doesn't exist"));

        jobApplicationRepository.delete(job);
    }

    public JobApplicationResponseDTO updateJob(Long id, UpdateJobRequestDTO dto) {

        JobApplication job = jobApplicationRepository.findByIdAndUserId(id, HelperFX.getCurrentUser().getId())
                .orElseThrow(() -> new JobApplicationIdNotExist("Job application id doesn't exist"));

        if (dto.getStatus() != null) {
            job.setStatus(dto.getStatus());
        }

        if (dto.getSalaryExpectation() != null) {
            job.setSalaryExceptation(dto.getSalaryExpectation());
        }

        if (dto.getLocation() != null) {
            job.setLocation(dto.getLocation());
        }

        if (dto.getJobType() != null) {
            job.setJobType(dto.getJobType());
        }

        if (dto.getJobDescription() != null) {
            job.setJobDescription(dto.getJobDescription());
        }

        if (dto.getNotes() != null) {
            job.setNotes(dto.getNotes());
        }

        return modelMapper.map(jobApplicationRepository.save(job), JobApplicationResponseDTO.class);
    }

    public long getTotalApplications() {
        return jobApplicationRepository.countByUserId(HelperFX.getCurrentUser().getId());
    }

    public long getTotalInterviews() {
        return jobApplicationRepository.countByUserIdAndStatus(HelperFX.getCurrentUser().getId(),
                ApplicationStatus.INTERVIEW);
    }

    public long getTotalOffers() {
        return jobApplicationRepository.countByUserIdAndStatus(HelperFX.getCurrentUser().getId(),
                ApplicationStatus.OFFER);
    }
}
