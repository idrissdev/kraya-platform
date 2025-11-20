package com.kraya.platform.service;

import com.kraya.platform.dto.DebtorRequest;
import com.kraya.platform.dto.DebtorResponse;
import com.kraya.platform.model.Debtor;

import java.util.List;

/**
 * DebtorService interface defines the contract for debtor-related operations.
 */
public interface DebtorService {

    /**
     * Retrieves all debtors.
     *
     * @return a list of debtors
     */
    List<Debtor> findAll();

    /**
     * Retrieves a debtor by ID.
     *
     * @param id the ID of the debtor to retrieve
     * @return the debtor object if found
     */
    Debtor findById(Long id);

    /**
     * Creates a new debtor based on the request.
     *
     * @param request the debtor registration request containing necessary data
     * @return the created debtor
     */
    Debtor create(DebtorRequest request);

    /**
     * Updates an existing debtor's information.
     *
     * @param id      the ID of the debtor to update
     * @param request the updated debtor data
     * @return the updated debtor
     */
    Debtor update(Long id, DebtorRequest request);

    /**
     * Deletes a debtor by ID.
     *
     * @param id the ID of the debtor to delete
     */
    void delete(Long id);

    /**
     * Converts a Debtor entity to a DebtorResponse DTO.
     *
     * @param debtor the debtor entity
     * @return the debtor response DTO
     */
    DebtorResponse toResponse(Debtor debtor);
}
