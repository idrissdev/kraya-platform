package com.kraya.platform.controller;

import com.kraya.platform.dto.DocumentRequest;
import com.kraya.platform.dto.DocumentResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Document;
import com.kraya.platform.service.DocumentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DocumentController handles document-related HTTP requests, providing endpoints
 * for document creation, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Creates a new document.
     *
     * @param request the document creation request
     * @return ResponseEntity with document response and created status
     */
    @PostMapping
    public ResponseEntity<DocumentResponse> createDocument(@Valid @RequestBody DocumentRequest request) {
        logger.info("Received request to create document for debtor ID: {}", request.getDebtorId());

        try {
            Document document = documentService.create(request);
            DocumentResponse response = new DocumentResponse(document.getDocumentId(), "Document created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DocumentResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DocumentResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all documents.
     *
     * @return ResponseEntity containing the list of documents
     */
    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        logger.info("Fetching all documents");
        List<Document> documents = documentService.findAll();
        return ResponseEntity.ok(documents);
    }

    /**
     * Retrieves a document by ID.
     *
     * @param documentId the ID of the document to retrieve
     * @return ResponseEntity containing the document data if found
     */
    @GetMapping("/{documentId}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long documentId) {
        logger.info("Fetching document with ID: {}", documentId);
        try {
            Document document = documentService.findById(documentId);
            return ResponseEntity.ok(document);
        } catch (ResourceNotFoundException e) {
            logger.error("Document not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all documents for a specific debtor.
     *
     * @param debtorId the debtor ID
     * @return ResponseEntity containing the list of documents
     */
    @GetMapping("/debtor/{debtorId}")
    public ResponseEntity<List<Document>> getDocumentsByDebtorId(@PathVariable Long debtorId) {
        logger.info("Fetching documents for debtor ID: {}", debtorId);
        try {
            List<Document> documents = documentService.findByDebtorId(debtorId);
            return ResponseEntity.ok(documents);
        } catch (ResourceNotFoundException e) {
            logger.error("Debtor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all documents by verification status.
     *
     * @param status the verification status
     * @return ResponseEntity containing the list of documents
     */
    @GetMapping("/verification-status/{status}")
    public ResponseEntity<List<Document>> getDocumentsByVerificationStatus(@PathVariable String status) {
        logger.info("Fetching documents with verification status: {}", status);
        List<Document> documents = documentService.findByVerificationStatus(status);
        return ResponseEntity.ok(documents);
    }

    /**
     * Retrieves all documents for a specific debtor by document type.
     *
     * @param debtorId the debtor ID
     * @param documentType the document type
     * @return ResponseEntity containing the list of documents
     */
    @GetMapping("/debtor/{debtorId}/type/{documentType}")
    public ResponseEntity<List<Document>> getDocumentsByDebtorIdAndType(@PathVariable Long debtorId, @PathVariable String documentType) {
        logger.info("Fetching documents for debtor ID: {} with document type: {}", debtorId, documentType);
        try {
            List<Document> documents = documentService.findByDebtorIdAndDocumentType(debtorId, documentType);
            return ResponseEntity.ok(documents);
        } catch (ResourceNotFoundException e) {
            logger.error("Debtor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates document information.
     *
     * @param documentId  the ID of the document to update
     * @param request the document update request
     * @return ResponseEntity with a success message
     */
    @PutMapping("/{documentId}")
    public ResponseEntity<DocumentResponse> updateDocument(@PathVariable Long documentId, @Valid @RequestBody DocumentRequest request) {
        logger.info("Updating document with ID: {}", documentId);

        try {
            documentService.update(documentId, request);
            return ResponseEntity.ok(new DocumentResponse(documentId, "Document updated successfully"));
        } catch (ResourceNotFoundException e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DocumentResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DocumentResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a document by ID.
     *
     * @param documentId the ID of the document to delete
     * @return ResponseEntity with No Content status if deletion is successful
     */
    @DeleteMapping("/{documentId}")
    public ResponseEntity<DocumentResponse> deleteDocument(@PathVariable Long documentId) {
        logger.info("Deleting document with ID: {}", documentId);

        try {
            documentService.delete(documentId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DocumentResponse(e.getMessage()));
        }
    }

    /**
     * Handles ResourceNotFoundException and returns a meaningful response.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with error message and HTTP status NOT_FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
