package com.ai.job_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.job_tracker.model.ResumeAnalysis;

@Repository
public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysis, Long> {

    List<ResumeAnalysis> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<ResumeAnalysis> findByIdAndUserId(Long id, Long userId);

    long countByUserId(Long userId);
}