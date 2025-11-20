package com.kraya.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for recommendation responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse {

    private Long recommendationId;
    private Long debtorId;
    private Long creditorId;
    private String comment;
    private LocalDateTime recommendationDate;
    private String message;

    public RecommendationResponse(String message) {
        this.message = message;
    }

    public RecommendationResponse(Long recommendationId, String message) {
        this.recommendationId = recommendationId;
        this.message = message;
    }
}
