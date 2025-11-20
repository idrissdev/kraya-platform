package com.kraya.platform.service;

import com.kraya.platform.dto.AssociationRequest;
import com.kraya.platform.dto.AssociationResponse;
import com.kraya.platform.model.Association;

import java.util.List;

/**
 * AssociationService interface defines the contract for association-related operations.
 */
public interface AssociationService {

    /**
     * Retrieves all associations.
     *
     * @return a list of associations
     */
    List<Association> findAll();

    /**
     * Retrieves an association by ID.
     *
     * @param id the ID of the association to retrieve
     * @return the association object if found
     */
    Association findById(Long id);

    /**
     * Creates a new association based on the request.
     *
     * @param request the association registration request containing necessary data
     * @return the created association
     */
    Association create(AssociationRequest request);

    /**
     * Updates an existing association's information.
     *
     * @param id      the ID of the association to update
     * @param request the updated association data
     * @return the updated association
     */
    Association update(Long id, AssociationRequest request);

    /**
     * Deletes an association by ID.
     *
     * @param id the ID of the association to delete
     */
    void delete(Long id);

    /**
     * Converts an Association entity to an AssociationResponse DTO.
     *
     * @param association the association entity
     * @return the association response DTO
     */
    AssociationResponse toResponse(Association association);
}
