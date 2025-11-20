package com.kraya.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for document responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {

    private Long documentId;
    private Long debtorId;
    private String documentType;
    private String documentPath;
    private LocalDateTime uploadDate;
    private String verificationStatus;
    private String message;

    public DocumentResponse(String message) {
        this.message = message;
    }

    public DocumentResponse(Long documentId, String message) {
        this.documentId = documentId;
        this.message = message;
    }
}
