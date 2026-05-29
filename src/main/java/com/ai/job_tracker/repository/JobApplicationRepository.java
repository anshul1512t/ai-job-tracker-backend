package com.ai.job_tracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ai.job_tracker.enums.ApplicationStatus;
import com.ai.job_tracker.model.JobApplication;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

        Page<JobApplication> findByUserId(Long userId, Pageable pageable);

        Optional<JobApplication> findByIdAndUserId(Long id, Long userId);

        Page<JobApplication> findByUserIdAndStatus(
                        Long userId,
                        ApplicationStatus status,
                        Pageable pageable);

        Page<JobApplication> findByUserIdAndJobTitleContainingIgnoreCase(
                        Long userId,
                        String keyword,
                        Pageable pageable);

        Page<JobApplication> findByUserIdAndStatusAndJobTitleContainingIgnoreCase(
                        Long userId,
                        ApplicationStatus status,
                        String keyword,
                        Pageable pageable);

        Long countByUserId(long id);

        long countByUserIdAndStatus(long id, ApplicationStatus status);

        Page<JobApplication> findByUserIdAndJobTitleContainingIgnoreCaseOrUserIdAndCompanyNameContainingIgnoreCase(
                        Long userId1,
                        String jobTitle,
                        Long userId2,
                        String companyName,
                        Pageable pageable);

        Page<JobApplication> findByUserIdAndStatusAndJobTitleContainingIgnoreCaseOrUserIdAndStatusAndCompanyNameContainingIgnoreCase(
                        Long userId1,
                        ApplicationStatus status1,
                        String jobTitle,
                        Long userId2,
                        ApplicationStatus status2,
                        String companyName,
                        Pageable pageable);

        @Query("""
                            SELECT j FROM JobApplication j
                            WHERE j.user.id = :userId
                            AND (
                                LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
                                OR LOWER(j.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            )
                        """)
        Page<JobApplication> searchByUserAndKeyword(
                        @Param("userId") Long userId,
                        @Param("keyword") String keyword,
                        Pageable pageable);

        @Query("""
                            SELECT j FROM JobApplication j
                            WHERE j.user.id = :userId
                            AND j.status = :status
                            AND (
                                LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
                                OR LOWER(j.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            )
                        """)
        Page<JobApplication> searchByUserStatusAndKeyword(
                        @Param("userId") Long userId,
                        @Param("status") ApplicationStatus status,
                        @Param("keyword") String keyword,
                        Pageable pageable);
}