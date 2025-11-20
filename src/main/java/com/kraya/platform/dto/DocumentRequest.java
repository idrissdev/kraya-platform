package com.kraya.platform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for document creation and update requests.
 */
@Data
public class DocumentRequest {

    @NotNull(message = "Debtor ID is required")
    private Long debtorId;

    @NotNull(message = "Document type is required")
    private String documentType;

    @NotNull(message = "Document path is required")
    private String documentPath;

    @NotNull(message = "Verification status is required")
    private String verificationStatus;
}
