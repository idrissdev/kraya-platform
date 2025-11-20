package com.kraya.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for payment responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private Long paymentId;
    private Long debtId;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime transactionDate;
    private String message;

    public PaymentResponse(String message) {
        this.message = message;
    }

    public PaymentResponse(Long paymentId, String message) {
        this.paymentId = paymentId;
        this.message = message;
    }
}
