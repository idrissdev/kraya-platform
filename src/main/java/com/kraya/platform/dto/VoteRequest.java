package com.kraya.platform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for vote creation and update requests.
 */
@Data
public class VoteRequest {

    @NotNull(message = "Debtor ID is required")
    private Long debtorId;

    @NotNull(message = "Creditor ID is required")
    private Long creditorId;

    @NotNull(message = "Vote is required")
    private Boolean vote;

    private String comment;
}
