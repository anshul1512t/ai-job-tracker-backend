package com.ai.job_tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.job_tracker.ai.dto.CoverLetterResponseDTO;
import com.ai.job_tracker.model.CoverLetter;
import com.ai.job_tracker.model.User;
import com.ai.job_tracker.repository.CoverLetterRepository;
import com.ai.job_tracker.utils.HelperFX;

@Service
public class CoverLetterService {

    @Autowired
    private CoverLetterRepository coverLetterRepository;

    public List<CoverLetterResponseDTO> getCoverLetters() {
        User user = HelperFX.getCurrentUser();

        List<CoverLetter> coverLetters = coverLetterRepository.findByUserId(user.getId());

        return coverLetters.stream()
                .map(coverLetter -> {
                    CoverLetterResponseDTO dto = new CoverLetterResponseDTO();

                    dto.setId(coverLetter.getId());
                    dto.setCoverLetter(coverLetter.getContent());
                    dto.setCreatedAt(coverLetter.getCreatedAt());
                    dto.setJobApplicationId(coverLetter.getJobApplication().getId());

                    return dto;
                })
                .toList();
    }

    public void deleteCoverLetterById(Long id) {

        coverLetterRepository.deleteById(id);
    }

}


