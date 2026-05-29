package com.ai.job_tracker.model;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ResumeAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String feedback;
    private double score;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String strengths;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String improvements;
    @CreatedDate
    private LocalDateTime createdAt;
}
