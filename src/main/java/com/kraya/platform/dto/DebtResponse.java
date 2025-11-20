package com.kraya.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for debt responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebtResponse {

    private Long debtId;
    private Long debtorId;
    private Long creditorId;
    private BigDecimal originalAmount;
    private BigDecimal currentAmount;
    private BigDecimal interestRate;
    private LocalDate dueDate;
    private String status;
    private LocalDateTime creationDate;
    private String message;

    public DebtResponse(String message) {
        this.message = message;
    }

    public DebtResponse(Long debtId, String message) {
        this.debtId = debtId;
        this.message = message;
    }
}
