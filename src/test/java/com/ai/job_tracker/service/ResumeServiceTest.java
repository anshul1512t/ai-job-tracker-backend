package com.ai.job_tracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.ai.job_tracker.dto.response.ResumeUploadResponseDTO;
import com.ai.job_tracker.model.Resume;
import com.ai.job_tracker.model.User;
import com.ai.job_tracker.repository.ResumeRepository;
import com.ai.job_tracker.utils.HelperFX;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResumeServiceTest {

    @Mock
    private ResumeRepository repository;

    @Mock
    private ResumeParserService parserService;

    @Mock
    private FileStorageService storageService;

    @InjectMocks
    private ResumeService resumeService;

    @Test
    void shouldUploadResume() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "resume.pdf",
                "application/pdf",
                "dummy resume content".getBytes());

        User user = new User();
        user.setId(1L);

        Resume savedResume = new Resume();
        savedResume.setId(1L);
        savedResume.setUser(user);
        savedResume.setOriginalFileName("resume.pdf");
        savedResume.setFileType("application/pdf");
        savedResume.setFileSize(file.getSize());
        savedResume.setExtractedText("Extracted resume text");
        savedResume.setStoredFileName("generated-resume.pdf");
        savedResume.setFilePath("/uploads/generated-resume.pdf");

        when(parserService.parse(file))
                .thenReturn("Extracted resume text");

        when(storageService.store(eq(file), anyString()))
                .thenReturn("/uploads/generated-resume.pdf");

        when(repository.save(any(Resume.class)))
                .thenReturn(savedResume);

        try (MockedStatic<HelperFX> helper = mockStatic(HelperFX.class)) {
            helper.when(HelperFX::getCurrentUser).thenReturn(user);

            ResumeUploadResponseDTO response = resumeService.upload(file);

            assertNotNull(response);
            assertEquals(1L, response.getId());

            verify(parserService).parse(file);
            verify(storageService).store(eq(file), anyString());
            verify(repository).save(any(Resume.class));
        }
    }

    @Test
    void shouldThrowExceptionWhenFileIsEmpty() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "empty.pdf",
                "application/pdf",
                new byte[0]);

        assertThrows(RuntimeException.class, () -> resumeService.upload(file));

        verify(repository, never()).save(any());
        verify(parserService, never()).parse(any());
        verify(storageService, never()).store(any(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenFileTypeIsInvalid() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "resume.txt",
                "text/plain",
                "dummy content".getBytes());

        assertThrows(RuntimeException.class, () -> resumeService.upload(file));

        verify(repository, never()).save(any());
        verify(parserService, never()).parse(any());
        verify(storageService, never()).store(any(), anyString());
    }

    @Test
    void shouldReturnResumeHistory() {
        User user = new User();
        user.setId(1L);

        Resume resume1 = new Resume();
        resume1.setId(1L);
        resume1.setStoredFileName("resume1.pdf");
        resume1.setUser(user);

        Resume resume2 = new Resume();
        resume2.setId(2L);
        resume2.setStoredFileName("resume2.pdf");
        resume2.setUser(user);

        when(repository.findByUser(user))
                .thenReturn(List.of(resume1, resume2));

        try (MockedStatic<HelperFX> helper = mockStatic(HelperFX.class)) {
            helper.when(HelperFX::getCurrentUser).thenReturn(user);

            List<ResumeUploadResponseDTO> result = resumeService.getAllResumes();

            assertEquals(2, result.size());
            verify(repository).findByUser(user);
        }
    }

    @Test
    void shouldDeleteResume() {
        Long resumeId = 1L;

        User user = new User();
        user.setId(1L);

        Resume resume = new Resume();
        resume.setId(resumeId);
        resume.setUser(user);
        resume.setStoredFileName("resume.pdf");

        when(repository.findById(resumeId))
                .thenReturn(Optional.of(resume));

        try (MockedStatic<HelperFX> helper = mockStatic(HelperFX.class)) {
            helper.when(HelperFX::getCurrentUser).thenReturn(user);

            resumeService.deleteResume(resumeId);

            verify(storageService).delete("resume.pdf");
            verify(repository).delete(resume);
        }
    }

    @Test
    void shouldThrowExceptionWhenResumeNotFound() {
        Long resumeId = 99L;

        User user = new User();
        user.setId(1L);

        when(repository.findById(resumeId))
                .thenReturn(Optional.empty());

        try (MockedStatic<HelperFX> helper = mockStatic(HelperFX.class)) {
            helper.when(HelperFX::getCurrentUser).thenReturn(user);

            assertThrows(RuntimeException.class,
                    () -> resumeService.deleteResume(resumeId));

            verify(repository, never()).delete(any());
            verify(storageService, never()).delete(anyString());
        }
    }

    @Test
    void shouldThrowExceptionWhenUserIsUnauthorized() {
        Long resumeId = 1L;

        User currentUser = new User();
        currentUser.setId(1L);

        User owner = new User();
        owner.setId(2L);

        Resume resume = new Resume();
        resume.setId(resumeId);
        resume.setUser(owner);
        resume.setStoredFileName("resume.pdf");

        when(repository.findById(resumeId))
                .thenReturn(Optional.of(resume));

        try (MockedStatic<HelperFX> helper = mockStatic(HelperFX.class)) {
            helper.when(HelperFX::getCurrentUser).thenReturn(currentUser);

            assertThrows(RuntimeException.class,
                    () -> resumeService.deleteResume(resumeId));

            verify(repository, never()).delete(any());
            verify(storageService, never()).delete(anyString());
        }
    }
}