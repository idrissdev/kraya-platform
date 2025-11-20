package com.kraya.platform.repository;

import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DocumentRepository provides methods for accessing document data.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    /**
     * Finds all documents for a specific debtor.
     *
     * @param debtor the debtor
     * @return list of documents
     */
    List<Document> findByDebtor(AppUser debtor);

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
     * @param debtor       the debtor
     * @param documentType the document type
     * @return list of documents
     */
    List<Document> findByDebtorAndDocumentType(AppUser debtor, String documentType);
}
