package com.kraya.platform.repository;

import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DebtRepository provides methods for accessing debt data.
 */
@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {

    /**
     * Finds all debts for a specific debtor.
     *
     * @param debtor the debtor
     * @return list of debts
     */
    List<Debt> findByDebtor(AppUser debtor);

    /**
     * Finds all debts for a specific creditor.
     *
     * @param creditor the creditor
     * @return list of debts
     */
    List<Debt> findByCreditor(AppUser creditor);

    /**
     * Finds all debts by status.
     *
     * @param status the debt status
     * @return list of debts
     */
    List<Debt> findByStatus(String status);

    /**
     * Finds all debts for a specific debtor by status.
     *
     * @param debtor the debtor
     * @param status the debt status
     * @return list of debts
     */
    List<Debt> findByDebtorAndStatus(AppUser debtor, String status);
}
