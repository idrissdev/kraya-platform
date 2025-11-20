package com.kraya.platform.service;

import com.kraya.platform.dto.DebtRequest;
import com.kraya.platform.model.Debt;

import java.util.List;

/**
 * DebtService interface defines the contract for debt-related operations.
 */
public interface DebtService {

    /**
     * Retrieves all debts.
     *
     * @return a list of debts
     */
    List<Debt> findAll();

    /**
     * Retrieves a debt by ID.
     *
     * @param id the ID of the debt to retrieve
     * @return the debt object if found
     */
    Debt findById(Long id);

    /**
     * Creates a new debt based on the request.
     *
     * @param request the debt request containing necessary data
     * @return the created debt
     */
    Debt create(DebtRequest request);

    /**
     * Updates an existing debt's information.
     *
     * @param id      the ID of the debt to update
     * @param request the updated debt data
     * @return the updated debt
     */
    Debt update(Long id, DebtRequest request);

    /**
     * Deletes a debt by ID.
     *
     * @param id the ID of the debt to delete
     */
    void delete(Long id);

    /**
     * Finds all debts for a specific debtor.
     *
     * @param debtorId the debtor ID
     * @return list of debts
     */
    List<Debt> findByDebtorId(Long debtorId);

    /**
     * Finds all debts for a specific creditor.
     *
     * @param creditorId the creditor ID
     * @return list of debts
     */
    List<Debt> findByCreditorId(Long creditorId);

    /**
     * Finds all debts by status.
     *
     * @param status the debt status
     * @return list of debts
     */
    List<Debt> findByStatus(String status);
}
