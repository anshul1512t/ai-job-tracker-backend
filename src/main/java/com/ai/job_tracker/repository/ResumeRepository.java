package com.ai.job_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.job_tracker.model.Resume;
import com.ai.job_tracker.model.User;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByUser(User user);

    Optional<Resume> findByIdAndUserId(Long resumeId, Long id);

    long countByUserId(Long userId);
}