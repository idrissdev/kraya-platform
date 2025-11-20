package com.kraya.platform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for recommendation creation and update requests.
 */
@Data
public class RecommendationRequest {

    @NotNull(message = "Debtor ID is required")
    private Long debtorId;

    @NotNull(message = "Creditor ID is required")
    private Long creditorId;

    private String comment;
}
