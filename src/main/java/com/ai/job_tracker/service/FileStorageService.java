package com.ai.job_tracker.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final String UPLOAD_DIR = "uploads/user_resumes/";

    public String store(MultipartFile file, String fileName) {

        try {

            Path uploadPath = Paths.get(UPLOAD_DIR);

            // create folder if not exists
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // full file path
            Path filePath = uploadPath.resolve(fileName);

            // save file
            Files.copy(file.getInputStream(), filePath);

            return filePath.toString();

        } catch (Exception e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public void delete(String fileName) {

        try {

            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            Files.deleteIfExists(filePath);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to delete file",
                    e);
        }
    }
}