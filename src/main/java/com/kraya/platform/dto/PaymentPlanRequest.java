package com.kraya.platform.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for payment plan creation and update requests.
 */
@Data
public class PaymentPlanRequest {

    @NotNull(message = "Debt ID is required")
    private Long debtId;

    @NotNull(message = "Creditor ID is required")
    private Long creditorId;

    @NotNull(message = "Plan type is required")
    private String planType;

    @NotNull(message = "Installment amount is required")
    @Positive(message = "Installment amount must be positive")
    private BigDecimal installmentAmount;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Status is required")
    private String status;
}
