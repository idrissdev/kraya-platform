package com.kraya.platform.service;

import com.kraya.platform.dto.RecommendationRequest;
import com.kraya.platform.model.Recommendation;

import java.util.List;

/**
 * RecommendationService interface defines the contract for recommendation-related operations.
 */
public interface RecommendationService {

    /**
     * Retrieves all recommendations.
     *
     * @return a list of recommendations
     */
    List<Recommendation> findAll();

    /**
     * Retrieves a recommendation by ID.
     *
     * @param id the ID of the recommendation to retrieve
     * @return the recommendation object if found
     */
    Recommendation findById(Long id);

    /**
     * Creates a new recommendation based on the request.
     *
     * @param request the recommendation request containing necessary data
     * @return the created recommendation
     */
    Recommendation create(RecommendationRequest request);

    /**
     * Updates an existing recommendation's information.
     *
     * @param id      the ID of the recommendation to update
     * @param request the updated recommendation data
     * @return the updated recommendation
     */
    Recommendation update(Long id, RecommendationRequest request);

    /**
     * Deletes a recommendation by ID.
     *
     * @param id the ID of the recommendation to delete
     */
    void delete(Long id);

    /**
     * Finds all recommendations for a specific debtor.
     *
     * @param debtorId the debtor ID
     * @return list of recommendations
     */
    List<Recommendation> findByDebtorId(Long debtorId);

    /**
     * Finds all recommendations from a specific creditor.
     *
     * @param creditorId the creditor ID
     * @return list of recommendations
     */
    List<Recommendation> findByCreditorId(Long creditorId);
}
