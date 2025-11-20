package com.kraya.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for debt transfer responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebtTransferResponse {

    private Long transferId;
    private Long debtId;
    private Long fromCreditorId;
    private Long toCreditorId;
    private LocalDateTime transferDate;
    private BigDecimal amount;
    private String status;
    private String message;

    public DebtTransferResponse(String message) {
        this.message = message;
    }

    public DebtTransferResponse(Long transferId, String message) {
        this.transferId = transferId;
        this.message = message;
    }
}
