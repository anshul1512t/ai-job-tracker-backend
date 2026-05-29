package com.ai.job_tracker.ai.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiFeedBackResponseDTO {

    private Integer score;

    private List<String> strengths;

    private List<String> improvements;

    private String summary;
}

