package com.kraya.platform.service.impl;

import com.kraya.platform.dto.DocumentRequest;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Document;
import com.kraya.platform.repository.DocumentRepository;
import com.kraya.platform.repository.UserRepository;
import com.kraya.platform.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DocumentServiceImpl provides the implementation for document-related operations.
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository,
                               UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Document> findAll() {
        logger.info("Fetching all documents");
        return documentRepository.findAll();
    }

    @Override
    public Document findById(Long id) {
        logger.info("Fetching document with ID: {}", id);
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + id));
    }

    @Override
    public Document create(DocumentRequest request) {
        logger.info("Creating new document for debtor ID: {}", request.getDebtorId());

        // Validate debtor exists
        AppUser debtor = (AppUser) userRepository.findById(request.getDebtorId())
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + request.getDebtorId()));

        Document document = new Document();
        document.setDebtor(debtor);
        document.setDocumentType(request.getDocumentType());
        document.setDocumentPath(request.getDocumentPath());
        document.setUploadDate(LocalDateTime.now());
        document.setVerificationStatus(request.getVerificationStatus());

        return documentRepository.save(document);
    }

    @Override
    public Document update(Long id, DocumentRequest request) {
        logger.info("Updating document with ID: {}", id);

        Document document = findById(id);

        // Update debtor if changed
        if (!document.getDebtor().getUserId().equals(request.getDebtorId())) {
            AppUser debtor = (AppUser) userRepository.findById(request.getDebtorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + request.getDebtorId()));
            document.setDebtor(debtor);
        }

        document.setDocumentType(request.getDocumentType());
        document.setDocumentPath(request.getDocumentPath());
        document.setVerificationStatus(request.getVerificationStatus());

        return documentRepository.save(document);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting document with ID: {}", id);
        Document document = findById(id);
        documentRepository.delete(document);
    }

    @Override
    public List<Document> findByDebtorId(Long debtorId) {
        logger.info("Fetching documents for debtor ID: {}", debtorId);
        AppUser debtor = (AppUser) userRepository.findById(debtorId)
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + debtorId));
        return documentRepository.findByDebtor(debtor);
    }

    @Override
    public List<Document> findByVerificationStatus(String verificationStatus) {
        logger.info("Fetching documents with verification status: {}", verificationStatus);
        return documentRepository.findByVerificationStatus(verificationStatus);
    }

    @Override
    public List<Document> findByDebtorIdAndDocumentType(Long debtorId, String documentType) {
        logger.info("Fetching documents for debtor ID: {} with document type: {}", debtorId, documentType);
        AppUser debtor = (AppUser) userRepository.findById(debtorId)
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + debtorId));
        return documentRepository.findByDebtorAndDocumentType(debtor, documentType);
    }
}
