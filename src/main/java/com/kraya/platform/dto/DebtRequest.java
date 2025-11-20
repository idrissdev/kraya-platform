package com.kraya.platform.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for debt creation and update requests.
 */
@Data
public class DebtRequest {

    @NotNull(message = "Debtor ID is required")
    private Long debtorId;

    @NotNull(message = "Creditor ID is required")
    private Long creditorId;

    @NotNull(message = "Original amount is required")
    @Positive(message = "Original amount must be positive")
    private BigDecimal originalAmount;

    @NotNull(message = "Current amount is required")
    @Positive(message = "Current amount must be positive")
    private BigDecimal currentAmount;

    private BigDecimal interestRate;

    private LocalDate dueDate;

    @NotNull(message = "Status is required")
    private String status;
}
