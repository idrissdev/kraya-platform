package com.kraya.platform.service;

import com.kraya.platform.dto.DocumentRequest;
import com.kraya.platform.model.Document;

import java.util.List;

/**
 * DocumentService interface defines the contract for document-related operations.
 */
public interface DocumentService {

    /**
     * Retrieves all documents.
     *
     * @return a list of documents
     */
    List<Document> findAll();

    /**
     * Retrieves a document by ID.
     *
     * @param id the ID of the document to retrieve
     * @return the document object if found
     */
    Document findById(Long id);

    /**
     * Creates a new document based on the request.
     *
     * @param request the document request containing necessary data
     * @return the created document
     */
    Document create(DocumentRequest request);

    /**
     * Updates an existing document's information.
     *
     * @param id      the ID of the document to update
     * @param request the updated document data
     * @return the updated document
     */
    Document update(Long id, DocumentRequest request);

    /**
     * Deletes a document by ID.
     *
     * @param id the ID of the document to delete
     */
    void delete(Long id);

    /**
     * Finds all documents for a specific debtor.
     *
     * @param debtorId the debtor ID
     * @return list of documents
     */
    List<Document> findByDebtorId(Long debtorId);

    /**
     * Finds all documents by verification status.
     *
     * @param verificationStatus the verification status
     * @return list of documents
     */
    List<Document> findByVerificationStatus(String verificationStatus);

    /**
     * Finds all documents for a specific debtor by document type.
     *
     * @param debtorId     the debtor ID
     * @param documentType the document type
     * @return list of documents
     */
    List<Document> findByDebtorIdAndDocumentType(Long debtorId, String documentType);
}
