package com.kraya.platform.service;

import com.kraya.platform.dto.CreditorRequest;
import com.kraya.platform.dto.CreditorResponse;
import com.kraya.platform.model.Creditor;

import java.util.List;

/**
 * CreditorService interface defines the contract for creditor-related operations.
 */
public interface CreditorService {

    /**
     * Retrieves all creditors.
     *
     * @return a list of creditors
     */
    List<Creditor> findAll();

    /**
     * Retrieves a creditor by ID.
     *
     * @param id the ID of the creditor to retrieve
     * @return the creditor object if found
     */
    Creditor findById(Long id);

    /**
     * Creates a new creditor based on the request.
     *
     * @param request the creditor registration request containing necessary data
     * @return the created creditor
     */
    Creditor create(CreditorRequest request);

    /**
     * Updates an existing creditor's information.
     *
     * @param id      the ID of the creditor to update
     * @param request the updated creditor data
     * @return the updated creditor
     */
    Creditor update(Long id, CreditorRequest request);

    /**
     * Deletes a creditor by ID.
     *
     * @param id the ID of the creditor to delete
     */
    void delete(Long id);

    /**
     * Converts a Creditor entity to a CreditorResponse DTO.
     *
     * @param creditor the creditor entity
     * @return the creditor response DTO
     */
    CreditorResponse toResponse(Creditor creditor);
}
