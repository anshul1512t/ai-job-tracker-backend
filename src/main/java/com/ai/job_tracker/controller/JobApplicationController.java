package com.ai.job_tracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.job_tracker.dto.request.AddJobApplicationRequestDTO;
import com.ai.job_tracker.dto.request.UpdateJobRequestDTO;
import com.ai.job_tracker.dto.response.CardDetailsDTO;
import com.ai.job_tracker.dto.response.JobApplicationResponseDTO;
import com.ai.job_tracker.enums.ApplicationStatus;
import com.ai.job_tracker.service.JobApplicationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplicationResponseDTO> addJobApplication(
            @Valid @RequestBody AddJobApplicationRequestDTO request) {
        return new ResponseEntity<JobApplicationResponseDTO>(jobApplicationService.createJob(request),
                HttpStatusCode.valueOf(201));
    }

    @GetMapping("/{jobId}")
    public JobApplicationResponseDTO getJobById(@PathVariable Long jobId) {
        return jobApplicationService.getJobById(jobId);
    }

    @GetMapping
    public Page<JobApplicationResponseDTO> getAllJobs(
            Pageable pageable,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) String keyword) {
        return jobApplicationService.getAlljobs(pageable, status, keyword);
    }

    @GetMapping("/cardDetails")
    public ResponseEntity<CardDetailsDTO> cardDetails() {
        return ResponseEntity.ok(new CardDetailsDTO(jobApplicationService.getTotalApplications(),jobApplicationService.getTotalInterviews(),jobApplicationService.getTotalOffers()));
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobApplicationService.deleteJob(id);
        return ResponseEntity.ok("Job Deleted Successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> updateJob(@PathVariable Long id,
            @RequestBody UpdateJobRequestDTO request) {
        return ResponseEntity.ok().body(jobApplicationService.updateJob(id, request));
    }
}
