package com.kraya.platform.controller;

import com.kraya.platform.dto.AssociationRequest;
import com.kraya.platform.dto.AssociationResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Association;
import com.kraya.platform.service.AssociationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AssociationController handles association-related HTTP requests, providing endpoints
 * for association registration, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/associations")
public class AssociationController {

    private static final Logger logger = LoggerFactory.getLogger(AssociationController.class);

    private final AssociationService associationService;

    @Autowired
    public AssociationController(AssociationService associationService) {
        this.associationService = associationService;
    }

    /**
     * Registers a new association.
     *
     * @param request the association registration request containing necessary data
     * @return ResponseEntity with association response and created status
     */
    @PostMapping
    public ResponseEntity<AssociationResponse> createAssociation(@Valid @RequestBody AssociationRequest request) {
        logger.info("Received registration request for association with username: {}", request.getUsername());

        try {
            Association createdAssociation = associationService.create(request);
            AssociationResponse response = associationService.toResponse(createdAssociation);
            response.setMessage("Association registered successfully");

            return ResponseEntity
                    .created(URI.create("/api/associations/" + createdAssociation.getUserId()))
                    .body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AssociationResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all associations.
     *
     * @return ResponseEntity containing list of all associations
     */
    @GetMapping
    public ResponseEntity<List<AssociationResponse>> getAllAssociations() {
        logger.info("Fetching all associations");
        List<Association> associations = associationService.findAll();
        List<AssociationResponse> responses = associations.stream()
                .map(associationService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves an association by ID.
     *
     * @param associationId the ID of the association to retrieve
     * @return ResponseEntity containing the association data if found, otherwise Not Found status
     */
    @GetMapping("/{associationId}")
    public ResponseEntity<AssociationResponse> getAssociationById(@PathVariable Long associationId) {
        logger.info("Fetching association with ID: {}", associationId);

        try {
            Association association = associationService.findById(associationId);
            AssociationResponse response = associationService.toResponse(association);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Association not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AssociationResponse(e.getMessage()));
        }
    }

    /**
     * Updates association information.
     *
     * @param associationId the ID of the association to update
     * @param request       the association update request containing updated data
     * @return ResponseEntity with a success message and OK status if update is successful, otherwise Not Found status
     */
    @PutMapping("/{associationId}")
    public ResponseEntity<AssociationResponse> updateAssociation(@PathVariable Long associationId,
                                                                  @Valid @RequestBody AssociationRequest request) {
        logger.info("Updating association with ID: {}", associationId);

        try {
            Association updatedAssociation = associationService.update(associationId, request);
            AssociationResponse response = associationService.toResponse(updatedAssociation);
            response.setMessage("Association updated successfully");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AssociationResponse(e.getMessage()));
        }
    }

    /**
     * Deletes an association by ID.
     *
     * @param associationId the ID of the association to delete
     * @return ResponseEntity with No Content status if deletion is successful, otherwise Not Found status
     */
    @DeleteMapping("/{associationId}")
    public ResponseEntity<String> deleteAssociation(@PathVariable Long associationId) {
        logger.info("Deleting association with ID: {}", associationId);

        try {
            associationService.delete(associationId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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

    /**
     * Handles MethodArgumentNotValidException and returns validation errors.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with validation error messages and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
