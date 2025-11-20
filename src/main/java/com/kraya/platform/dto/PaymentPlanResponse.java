package com.kraya.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for payment plan responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPlanResponse {

    private Long planId;
    private Long debtId;
    private Long creditorId;
    private String planType;
    private BigDecimal installmentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String message;

    public PaymentPlanResponse(String message) {
        this.message = message;
    }

    public PaymentPlanResponse(Long planId, String message) {
        this.planId = planId;
        this.message = message;
    }
}
