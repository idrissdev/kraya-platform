package com.kraya.platform.service;

import com.kraya.platform.dto.DebtTransferRequest;
import com.kraya.platform.model.DebtTransfer;

import java.util.List;

/**
 * DebtTransferService interface defines the contract for debt transfer-related operations.
 */
public interface DebtTransferService {

    /**
     * Retrieves all debt transfers.
     *
     * @return a list of debt transfers
     */
    List<DebtTransfer> findAll();

    /**
     * Retrieves a debt transfer by ID.
     *
     * @param id the ID of the debt transfer to retrieve
     * @return the debt transfer object if found
     */
    DebtTransfer findById(Long id);

    /**
     * Creates a new debt transfer based on the request.
     *
     * @param request the debt transfer request containing necessary data
     * @return the created debt transfer
     */
    DebtTransfer create(DebtTransferRequest request);

    /**
     * Updates an existing debt transfer's information.
     *
     * @param id      the ID of the debt transfer to update
     * @param request the updated debt transfer data
     * @return the updated debt transfer
     */
    DebtTransfer update(Long id, DebtTransferRequest request);

    /**
     * Deletes a debt transfer by ID.
     *
     * @param id the ID of the debt transfer to delete
     */
    void delete(Long id);

    /**
     * Finds all debt transfers for a specific debt.
     *
     * @param debtId the debt ID
     * @return list of debt transfers
     */
    List<DebtTransfer> findByDebtId(Long debtId);

    /**
     * Finds all debt transfers from a specific creditor.
     *
     * @param fromCreditorId the originating creditor ID
     * @return list of debt transfers
     */
    List<DebtTransfer> findByFromCreditorId(Long fromCreditorId);

    /**
     * Finds all debt transfers to a specific creditor.
     *
     * @param toCreditorId the receiving creditor ID
     * @return list of debt transfers
     */
    List<DebtTransfer> findByToCreditorId(Long toCreditorId);

    /**
     * Finds all debt transfers by status.
     *
     * @param status the transfer status
     * @return list of debt transfers
     */
    List<DebtTransfer> findByStatus(String status);
}
