package com.ai.job_tracker.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ai.job_tracker.dto.response.ResumeUploadResponseDTO;
import com.ai.job_tracker.exception.EmptyFileException;
import com.ai.job_tracker.exception.InvalidFileFormatException;
import com.ai.job_tracker.model.Resume;
import com.ai.job_tracker.model.User;
import com.ai.job_tracker.repository.ResumeRepository;
import com.ai.job_tracker.utils.HelperFX;
import java.util.UUID;

@Service
public class ResumeService {
    @Autowired
    private ResumeParserService parserService;
    @Autowired
    private FileStorageService storageService;
    @Autowired
    private ResumeRepository repository;

    private static final List<String> allowedTypes = List.of(
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    public ResumeUploadResponseDTO upload(MultipartFile file) throws IOException {

        validateFile(file);

        User user = HelperFX.getCurrentUser();
        Resume resume = new Resume();
        resume.setUser(user);
        resume.setOriginalFileName(file.getOriginalFilename());
        resume.setFileType(file.getContentType());
        resume.setFileSize(file.getSize());

        String extractedText = parserService.parse(file);

        resume.setExtractedText(extractedText);
        String fileName = generateFileName(file);

        String filePath = storageService.store(file, fileName);

        resume.setStoredFileName(fileName);
        resume.setFilePath(filePath);

        Resume savedResume = repository.save(resume);

        return new ResumeUploadResponseDTO(savedResume.getId(), savedResume.getStoredFileName(),
                savedResume.getCreatedAt());
    }

    public List<ResumeUploadResponseDTO> getAllResumes() {

        User user = HelperFX.getCurrentUser();

        List<Resume> resumes = repository.findByUser(user);

        return resumes.stream()
                .map(this::mapToDTO)
                .toList();
    }

    private ResumeUploadResponseDTO mapToDTO(Resume resume) {

        return new ResumeUploadResponseDTO(
                resume.getId(),
                resume.getStoredFileName(),
                resume.getCreatedAt());
    }

    private String generateFileName(MultipartFile file) {

        String originalName = file.getOriginalFilename();

        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        return UUID.randomUUID() + extension;
    }

    private void validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty");
        }

        String fileType = file.getContentType();

        if (fileType == null || !allowedTypes.contains(fileType)) {
            throw new InvalidFileFormatException(
                    "Only PDF and DOCX allowed");
        }

    }

    public void deleteResume(Long id) {

        User user = HelperFX.getCurrentUser();

        Resume resume = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        if (!resume.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        storageService.delete(resume.getStoredFileName());

        repository.delete(resume);
    }
}
