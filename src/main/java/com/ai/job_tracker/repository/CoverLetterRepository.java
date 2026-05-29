package com.ai.job_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.job_tracker.model.CoverLetter;

@Repository
public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {

    List<CoverLetter> findByIdAndUserId(Long id, Long userId);

    List<CoverLetter> findByUserId(Long userId);

    long countByUserId(Long userId);
}