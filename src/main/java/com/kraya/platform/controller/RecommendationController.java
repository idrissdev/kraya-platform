package com.kraya.platform.controller;

import com.kraya.platform.dto.RecommendationRequest;
import com.kraya.platform.dto.RecommendationResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Recommendation;
import com.kraya.platform.service.RecommendationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RecommendationController handles recommendation-related HTTP requests, providing endpoints
 * for recommendation creation, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationController.class);

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Creates a new recommendation.
     *
     * @param request the recommendation creation request
     * @return ResponseEntity with recommendation response and created status
     */
    @PostMapping
    public ResponseEntity<RecommendationResponse> createRecommendation(@Valid @RequestBody RecommendationRequest request) {
        logger.info("Received request to create recommendation for debtor ID: {}", request.getDebtorId());

        try {
            Recommendation recommendation = recommendationService.create(request);
            RecommendationResponse response = new RecommendationResponse(recommendation.getRecommendationId(), "Recommendation created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RecommendationResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RecommendationResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all recommendations.
     *
     * @return ResponseEntity containing the list of recommendations
     */
    @GetMapping
    public ResponseEntity<List<Recommendation>> getAllRecommendations() {
        logger.info("Fetching all recommendations");
        List<Recommendation> recommendations = recommendationService.findAll();
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Retrieves a recommendation by ID.
     *
     * @param recommendationId the ID of the recommendation to retrieve
     * @return ResponseEntity containing the recommendation data if found
     */
    @GetMapping("/{recommendationId}")
    public ResponseEntity<Recommendation> getRecommendationById(@PathVariable Long recommendationId) {
        logger.info("Fetching recommendation with ID: {}", recommendationId);
        try {
            Recommendation recommendation = recommendationService.findById(recommendationId);
            return ResponseEntity.ok(recommendation);
        } catch (ResourceNotFoundException e) {
            logger.error("Recommendation not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all recommendations for a specific debtor.
     *
     * @param debtorId the debtor ID
     * @return ResponseEntity containing the list of recommendations
     */
    @GetMapping("/debtor/{debtorId}")
    public ResponseEntity<List<Recommendation>> getRecommendationsByDebtorId(@PathVariable Long debtorId) {
        logger.info("Fetching recommendations for debtor ID: {}", debtorId);
        try {
            List<Recommendation> recommendations = recommendationService.findByDebtorId(debtorId);
            return ResponseEntity.ok(recommendations);
        } catch (ResourceNotFoundException e) {
            logger.error("Debtor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all recommendations from a specific creditor.
     *
     * @param creditorId the creditor ID
     * @return ResponseEntity containing the list of recommendations
     */
    @GetMapping("/creditor/{creditorId}")
    public ResponseEntity<List<Recommendation>> getRecommendationsByCreditorId(@PathVariable Long creditorId) {
        logger.info("Fetching recommendations from creditor ID: {}", creditorId);
        try {
            List<Recommendation> recommendations = recommendationService.findByCreditorId(creditorId);
            return ResponseEntity.ok(recommendations);
        } catch (ResourceNotFoundException e) {
            logger.error("Creditor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates recommendation information.
     *
     * @param recommendationId  the ID of the recommendation to update
     * @param request the recommendation update request
     * @return ResponseEntity with a success message
     */
    @PutMapping("/{recommendationId}")
    public ResponseEntity<RecommendationResponse> updateRecommendation(@PathVariable Long recommendationId, @Valid @RequestBody RecommendationRequest request) {
        logger.info("Updating recommendation with ID: {}", recommendationId);

        try {
            recommendationService.update(recommendationId, request);
            return ResponseEntity.ok(new RecommendationResponse(recommendationId, "Recommendation updated successfully"));
        } catch (ResourceNotFoundException e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RecommendationResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RecommendationResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a recommendation by ID.
     *
     * @param recommendationId the ID of the recommendation to delete
     * @return ResponseEntity with No Content status if deletion is successful
     */
    @DeleteMapping("/{recommendationId}")
    public ResponseEntity<RecommendationResponse> deleteRecommendation(@PathVariable Long recommendationId) {
        logger.info("Deleting recommendation with ID: {}", recommendationId);

        try {
            recommendationService.delete(recommendationId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RecommendationResponse(e.getMessage()));
        }
    }

    /**
     * Handles ResourceNotFoundException and returns a meaningful response.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with error message and HTTP status NOT_FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
