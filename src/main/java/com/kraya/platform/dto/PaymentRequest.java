package com.kraya.platform.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for payment creation requests.
 */
@Data
public class PaymentRequest {

    @NotNull(message = "Debt ID is required")
    private Long debtId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Payment method is required")
    private String paymentMethod;
}
