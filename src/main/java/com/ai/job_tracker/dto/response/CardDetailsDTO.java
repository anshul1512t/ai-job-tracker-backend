package com.ai.job_tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CardDetailsDTO {
    Long totalApplication;
    Long totalInterviews;
    Long totalOffers;
}
