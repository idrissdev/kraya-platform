package com.kraya.platform.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for debt transfer creation and update requests.
 */
@Data
public class DebtTransferRequest {

    @NotNull(message = "Debt ID is required")
    private Long debtId;

    @NotNull(message = "From creditor ID is required")
    private Long fromCreditorId;

    @NotNull(message = "To creditor ID is required")
    private Long toCreditorId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Status is required")
    private String status;
}
