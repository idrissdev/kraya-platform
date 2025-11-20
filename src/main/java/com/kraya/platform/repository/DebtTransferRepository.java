package com.kraya.platform.repository;

import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Debt;
import com.kraya.platform.model.DebtTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DebtTransferRepository provides methods for accessing debt transfer data.
 */
@Repository
public interface DebtTransferRepository extends JpaRepository<DebtTransfer, Long> {

    /**
     * Finds all transfers for a specific debt.
     *
     * @param debt the debt
     * @return list of debt transfers
     */
    List<DebtTransfer> findByDebt(Debt debt);

    /**
     * Finds all transfers from a specific creditor.
     *
     * @param fromCreditor the originating creditor
     * @return list of debt transfers
     */
    List<DebtTransfer> findByFromCreditor(AppUser fromCreditor);

    /**
     * Finds all transfers to a specific creditor.
     *
     * @param toCreditor the receiving creditor
     * @return list of debt transfers
     */
    List<DebtTransfer> findByToCreditor(AppUser toCreditor);

    /**
     * Finds all transfers by status.
     *
     * @param status the transfer status
     * @return list of debt transfers
     */
    List<DebtTransfer> findByStatus(String status);
}
