package com.ai.job_tracker.ai.service;

import com.ai.job_tracker.repository.CoverLetterRepository;
import com.ai.job_tracker.repository.JobApplicationRepository;
import com.ai.job_tracker.repository.ResumeAnalysisRepository;
import com.ai.job_tracker.repository.ResumeRepository;
import com.ai.job_tracker.repository.SkillGapAnalysisRepository;
import com.ai.job_tracker.utils.HelperFX;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import com.ai.job_tracker.ai.dto.CoverLetterResponseDTO;
import com.ai.job_tracker.ai.dto.CoverletterRequestDTO;
import com.ai.job_tracker.ai.dto.ResumeFeedbackRequestDTO;
import com.ai.job_tracker.ai.dto.ResumeFeedbackResponseDTO;
import com.ai.job_tracker.ai.dto.SkillGapResponseDTO;
import com.ai.job_tracker.ai.prompts.CoverLetterPromptTemplate;
import com.ai.job_tracker.ai.prompts.ResumeFeedbackPromptTemplate;
import com.ai.job_tracker.ai.prompts.SkillGapPromptTemplate;
import com.ai.job_tracker.model.ResumeAnalysis;
import com.ai.job_tracker.model.CoverLetter;
import com.ai.job_tracker.model.JobApplication;
import com.ai.job_tracker.model.Resume;
import com.ai.job_tracker.model.SkillGapAnalysis;
import com.ai.job_tracker.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AiService {
        private final JobApplicationRepository jobApplicationRepository;
        private final ResumeRepository resumeRepository;
        private final ResumeAnalysisRepository resumeAnalysisRepository;
        private final CoverLetterRepository coverLetterRepository;
        private final SkillGapAnalysisRepository skillGapAnalysisRepository;
        private final ChatClient chatClient;
        private final ObjectMapper objectMapper;

        public AiService(ChatClient.Builder chatClientBuilder, ResumeAnalysisRepository resumeAnalysisRepository,
                        ResumeRepository resumeRepository, JobApplicationRepository jobApplicationRepository,
                        CoverLetterRepository coverLetterRepository,
                        SkillGapAnalysisRepository skillGapAnalysisRepository,
                        ObjectMapper objectMapper) {
                this.chatClient = chatClientBuilder.build();
                this.resumeAnalysisRepository = resumeAnalysisRepository;
                this.resumeRepository = resumeRepository;
                this.jobApplicationRepository = jobApplicationRepository;
                this.coverLetterRepository = coverLetterRepository;
                this.skillGapAnalysisRepository = skillGapAnalysisRepository;
                this.objectMapper = objectMapper;
        }

        public String testingAi() {
                return chatClient
                                .prompt()
                                .user("just tell a joke of one line.")
                                .call()
                                .content();
        }

        public ResumeFeedbackResponseDTO resumeFeedback(ResumeFeedbackRequestDTO request)
                        throws JsonProcessingException {

                User user = HelperFX.getCurrentUser();

                Resume resume = resumeRepository
                                .findByIdAndUserId(request.getResumeId(), user.getId())
                                .orElseThrow();

                PromptTemplate template = ResumeFeedbackPromptTemplate.template;

                Prompt prompt = template.create(Map.of(
                                "resume", resume.getExtractedText()));

                ResumeFeedbackResponseDTO aiResponse = chatClient
                                .prompt(prompt)
                                .call()
                                .entity(ResumeFeedbackResponseDTO.class);

                ResumeAnalysis resumeAnalysis = new ResumeAnalysis();

                resumeAnalysis.setUser(user);
                resumeAnalysis.setResume(resume);
                resumeAnalysis.setScore(aiResponse.getScore());

                resumeAnalysis.setStrengths(
                                String.join(",", aiResponse.getStrengths()));

                resumeAnalysis.setImprovements(
                                String.join(",", aiResponse.getImprovements()));

                resumeAnalysis.setFeedback(aiResponse.getSummary());

                resumeAnalysis = resumeAnalysisRepository.save(resumeAnalysis);

                ResumeFeedbackResponseDTO response = new ResumeFeedbackResponseDTO();

                response.setResumeAnalysisId(resumeAnalysis.getId());
                response.setScore(aiResponse.getScore());
                response.setStrengths(aiResponse.getStrengths());
                response.setImprovements(aiResponse.getImprovements());
                response.setSummary(aiResponse.getSummary());
                response.setCreatedAt(resumeAnalysis.getCreatedAt());

                return response;
        }

        public CoverLetterResponseDTO coverLetterGeneration(CoverletterRequestDTO request) {
                User user = HelperFX.getCurrentUser();
                JobApplication jobApplication = jobApplicationRepository
                                .findByIdAndUserId(request.getJobApplicationId(), user.getId()).orElseThrow();
                Resume resume = resumeRepository.findByIdAndUserId(request.getResumeId(), user.getId()).orElseThrow();

                PromptTemplate template = CoverLetterPromptTemplate.template;

                Prompt prompt = template.create(Map.of(
                                "resume", resume.getExtractedText(),
                                "jobDescription", jobApplication.getJobDescription()));

                CoverLetterResponseDTO response = chatClient.prompt(prompt)
                                .call()
                                .entity(CoverLetterResponseDTO.class);

                CoverLetter coverLetter = new CoverLetter();
                coverLetter.setUser(user);
                coverLetter.setJobApplication(jobApplication);
                coverLetter.setContent(response.getCoverLetter());

                CoverLetter saved = coverLetterRepository.save(coverLetter);
                response.setCreatedAt(saved.getCreatedAt());
                response.setId(saved.getId());
                
                return response;
        }

        public SkillGapResponseDTO skillGapAnalysis(CoverletterRequestDTO request) {

                User user = HelperFX.getCurrentUser();
                JobApplication jobApplication = jobApplicationRepository
                                .findByIdAndUserId(request.getJobApplicationId(), user.getId()).orElseThrow();
                Resume resume = resumeRepository.findByIdAndUserId(request.getResumeId(), user.getId()).orElseThrow();

                PromptTemplate template = SkillGapPromptTemplate.template;

                Prompt prompt = template.create(Map.of(
                                "resume", resume.getExtractedText(),
                                "jobDescription", jobApplication.getJobDescription()));

                SkillGapResponseDTO response = chatClient
                                .prompt(prompt)
                                .call()
                                .entity(SkillGapResponseDTO.class);

                SkillGapAnalysis analysis = new SkillGapAnalysis();

                analysis.setUser(user);
                analysis.setJobApplication(jobApplication);
                analysis.setMatchPercentage(response.getMatchPercentage());

                analysis.setMissingSkills(
                                String.join(",", response.getMissingSkills()));

                analysis.setRecommendedSkills(
                                String.join(",", response.getRecommendedSkills()));

                analysis.setAnalysisText(response.getAnalysisText());

                skillGapAnalysisRepository.save(analysis);

                return response;
        }

        // get resume analysis
        public List<ResumeFeedbackResponseDTO> getUserFeedbacks() {

                User user = HelperFX.getCurrentUser();

                List<ResumeAnalysis> feedbacks = resumeAnalysisRepository
                                .findByUserIdOrderByCreatedAtDesc(user.getId());

                return feedbacks.stream()
                                .map(this::mapToResponseDTO)
                                .toList();
        }

        private ResumeFeedbackResponseDTO mapToResponseDTO(
                        ResumeAnalysis feedback) {

                ResumeFeedbackResponseDTO response = new ResumeFeedbackResponseDTO();

                response.setResumeAnalysisId(feedback.getId());

                response.setScore(feedback.getScore());

                response.setStrengths(
                                Arrays.asList(feedback.getStrengths().split(",")));

                response.setImprovements(
                                Arrays.asList(feedback.getImprovements().split(",")));

                response.setSummary(feedback.getFeedback());

                response.setCreatedAt(feedback.getCreatedAt());

                return response;
        }

        public ResumeFeedbackResponseDTO getFeedbackById(Long id) {

                User user = HelperFX.getCurrentUser();

                ResumeAnalysis feedback = resumeAnalysisRepository
                                .findByIdAndUserId(id, user.getId())
                                .orElseThrow();

                return mapToResponseDTO(feedback);
        }

        public void deleteFeedback(Long id) {

                User user = HelperFX.getCurrentUser();

                ResumeAnalysis feedback = resumeAnalysisRepository
                                .findByIdAndUserId(id, user.getId())
                                .orElseThrow();

                resumeAnalysisRepository.delete(feedback);
        }
}
