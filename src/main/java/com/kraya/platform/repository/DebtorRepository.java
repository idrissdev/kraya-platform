package com.kraya.platform.repository;

import com.kraya.platform.model.Debtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DebtorRepository provides methods for accessing debtor data.
 */
@Repository
public interface DebtorRepository extends JpaRepository<Debtor, Long> {

    /**
     * Finds a debtor by email.
     *
     * @param email the email
     * @return optional containing the debtor if found
     */
    Optional<Debtor> findByEmail(String email);

    /**
     * Finds all debtors by employment status.
     *
     * @param employmentStatus the employment status
     * @return list of debtors
     */
    List<Debtor> findByEmploymentStatus(String employmentStatus);
}
