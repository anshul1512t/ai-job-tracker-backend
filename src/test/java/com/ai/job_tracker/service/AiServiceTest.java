package com.ai.job_tracker.service;

import com.ai.job_tracker.ai.dto.ResumeFeedbackResponseDTO;
import com.ai.job_tracker.ai.service.AiService;
import com.ai.job_tracker.dto.response.ResumeUploadResponseDTO;
import com.ai.job_tracker.model.Resume;
import com.ai.job_tracker.model.ResumeAnalysis;
import com.ai.job_tracker.model.User;
import com.ai.job_tracker.repository.CoverLetterRepository;
import com.ai.job_tracker.repository.JobApplicationRepository;
import com.ai.job_tracker.repository.ResumeAnalysisRepository;
import com.ai.job_tracker.repository.ResumeRepository;
import com.ai.job_tracker.repository.SkillGapAnalysisRepository;
import com.ai.job_tracker.service.ResumeParserService;
import com.ai.job_tracker.utils.HelperFX;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ai.job_tracker.service.FileStorageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AiServiceTest {

    @Mock
    private ResumeRepository resumeRepository;

    @Mock
    private ResumeAnalysisRepository resumeAnalysisRepository;

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private CoverLetterRepository coverLetterRepository;

    @Mock
    private SkillGapAnalysisRepository skillGapAnalysisRepository;

    @Mock
    private ChatClient.Builder chatClientBuilder;

    @Mock
    private ChatClient chatClient;

    private AiService aiService;

    @BeforeEach
    void setUp() {
        when(chatClientBuilder.build()).thenReturn(chatClient);

        aiService = new AiService(
                chatClientBuilder,
                resumeAnalysisRepository,
                resumeRepository,
                jobApplicationRepository,
                coverLetterRepository,
                skillGapAnalysisRepository,
                new ObjectMapper());
    }

    @Test
    void shouldGetFeedbackById() {

        User user = new User();
        user.setId(1L);

        ResumeAnalysis analysis = new ResumeAnalysis();
        analysis.setId(10L);
        analysis.setScore(85);
        analysis.setStrengths("Java,Spring");
        analysis.setImprovements("Docker,AWS");
        analysis.setFeedback("Good Resume");

        try (MockedStatic<HelperFX> helper = mockStatic(HelperFX.class)) {

            helper.when(HelperFX::getCurrentUser)
                    .thenReturn(user);

            when(resumeAnalysisRepository
                    .findByIdAndUserId(10L, 1L))
                    .thenReturn(Optional.of(analysis));

            ResumeFeedbackResponseDTO result = aiService.getFeedbackById(10L);

            assertNotNull(result);
            assertEquals(85, result.getScore());
            assertEquals("Good Resume", result.getSummary());
        }
    }

    @Test
    void shouldGetUserFeedbacks() {

        User user = new User();
        user.setId(1L);

        ResumeAnalysis analysis = new ResumeAnalysis();
        analysis.setId(1L);
        analysis.setScore(90);
        analysis.setStrengths("Java,Spring");
        analysis.setImprovements("Docker");
        analysis.setFeedback("Strong Profile");

        try (MockedStatic<HelperFX> helper = mockStatic(HelperFX.class)) {

            helper.when(HelperFX::getCurrentUser)
                    .thenReturn(user);

            when(resumeAnalysisRepository
                    .findByUserIdOrderByCreatedAtDesc(1L))
                    .thenReturn(List.of(analysis));

            List<ResumeFeedbackResponseDTO> result = aiService.getUserFeedbacks();

            assertEquals(1, result.size());
            assertEquals(90, result.get(0).getScore());
        }
    }

    @Test
    void shouldDeleteFeedback() {

        User user = new User();
        user.setId(1L);

        ResumeAnalysis analysis = new ResumeAnalysis();
        analysis.setId(5L);

        try (MockedStatic<HelperFX> helper = mockStatic(HelperFX.class)) {

            helper.when(HelperFX::getCurrentUser)
                    .thenReturn(user);

            when(resumeAnalysisRepository
                    .findByIdAndUserId(5L, 1L))
                    .thenReturn(Optional.of(analysis));

            aiService.deleteFeedback(5L);

            verify(resumeAnalysisRepository)
                    .delete(analysis);
        }
    }

    @Test
    void shouldThrowWhenFeedbackNotFound() {

        User user = new User();
        user.setId(1L);

        try (MockedStatic<HelperFX> helper = mockStatic(HelperFX.class)) {

            helper.when(HelperFX::getCurrentUser)
                    .thenReturn(user);

            when(resumeAnalysisRepository
                    .findByIdAndUserId(99L, 1L))
                    .thenReturn(Optional.empty());

            assertThrows(NoSuchElementException.class,
                    () -> aiService.getFeedbackById(99L));
        }
    }

}