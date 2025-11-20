package com.kraya.platform.repository;

import com.kraya.platform.model.Creditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CreditorRepository provides methods for accessing creditor data.
 */
@Repository
public interface CreditorRepository extends JpaRepository<Creditor, Long> {

    /**
     * Finds a creditor by email.
     *
     * @param email the email
     * @return optional containing the creditor if found
     */
    Optional<Creditor> findByEmail(String email);

    /**
     * Finds all verified creditors.
     *
     * @param verified the verification status
     * @return list of creditors
     */
    List<Creditor> findByVerified(Boolean verified);

    /**
     * Finds a creditor by API key.
     *
     * @param apiKey the API key
     * @return optional containing the creditor if found
     */
    Optional<Creditor> findByApiKey(String apiKey);
}
