package com.ai.job_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ai.job_tracker.model.SkillGapAnalysis;

@Repository
public interface SkillGapAnalysisRepository extends JpaRepository<SkillGapAnalysis, Long> {

    List<SkillGapAnalysis> findByUserId(Long userId);

    Optional<SkillGapAnalysis> findByIdAndUserId(Long id, Long userId);

    long countByUserId(Long userId);

    @Query("""
        SELECT COALESCE(AVG(s.matchPercentage), 0)
        FROM SkillGapAnalysis s
        WHERE s.user.id = :userId
    """)
    Double findAverageMatchPercentageByUserId(@Param("userId") Long userId);
}