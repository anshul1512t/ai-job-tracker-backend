package com.ai.job_tracker.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ai.job_tracker.enums.ApplicationStatus;
import com.ai.job_tracker.enums.JobType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String jobTitle;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    private String location;
    private Long salaryExceptation;
    private LocalDate applicationDate;
    private String jobUrl;
    @Column(columnDefinition = "LONGTEXT")
    private String jobDescription;
    private String notes;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false, updatable = true)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
