package com.kraya.platform.controller;

import com.kraya.platform.dto.DebtorRequest;
import com.kraya.platform.dto.DebtorResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Debtor;
import com.kraya.platform.service.DebtorService;
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
 * DebtorController handles debtor-related HTTP requests, providing endpoints
 * for debtor registration, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/debtors")
public class DebtorController {

    private static final Logger logger = LoggerFactory.getLogger(DebtorController.class);

    private final DebtorService debtorService;

    @Autowired
    public DebtorController(DebtorService debtorService) {
        this.debtorService = debtorService;
    }

    /**
     * Registers a new debtor.
     *
     * @param request the debtor registration request containing necessary data
     * @return ResponseEntity with debtor response and created status
     */
    @PostMapping
    public ResponseEntity<DebtorResponse> createDebtor(@Valid @RequestBody DebtorRequest request) {
        logger.info("Received registration request for debtor with username: {}", request.getUsername());

        try {
            Debtor createdDebtor = debtorService.create(request);
            DebtorResponse response = debtorService.toResponse(createdDebtor);
            response.setMessage("Debtor registered successfully");

            return ResponseEntity
                    .created(URI.create("/api/debtors/" + createdDebtor.getUserId()))
                    .body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new DebtorResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all debtors.
     *
     * @return ResponseEntity containing list of all debtors
     */
    @GetMapping
    public ResponseEntity<List<DebtorResponse>> getAllDebtors() {
        logger.info("Fetching all debtors");
        List<Debtor> debtors = debtorService.findAll();
        List<DebtorResponse> responses = debtors.stream()
                .map(debtorService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves a debtor by ID.
     *
     * @param debtorId the ID of the debtor to retrieve
     * @return ResponseEntity containing the debtor data if found, otherwise Not Found status
     */
    @GetMapping("/{debtorId}")
    public ResponseEntity<DebtorResponse> getDebtorById(@PathVariable Long debtorId) {
        logger.info("Fetching debtor with ID: {}", debtorId);

        try {
            Debtor debtor = debtorService.findById(debtorId);
            DebtorResponse response = debtorService.toResponse(debtor);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Debtor not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DebtorResponse(e.getMessage()));
        }
    }

    /**
     * Updates debtor information.
     *
     * @param debtorId the ID of the debtor to update
     * @param request  the debtor update request containing updated data
     * @return ResponseEntity with a success message and OK status if update is successful, otherwise Not Found status
     */
    @PutMapping("/{debtorId}")
    public ResponseEntity<DebtorResponse> updateDebtor(@PathVariable Long debtorId,
                                                        @Valid @RequestBody DebtorRequest request) {
        logger.info("Updating debtor with ID: {}", debtorId);

        try {
            Debtor updatedDebtor = debtorService.update(debtorId, request);
            DebtorResponse response = debtorService.toResponse(updatedDebtor);
            response.setMessage("Debtor updated successfully");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DebtorResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a debtor by ID.
     *
     * @param debtorId the ID of the debtor to delete
     * @return ResponseEntity with No Content status if deletion is successful, otherwise Not Found status
     */
    @DeleteMapping("/{debtorId}")
    public ResponseEntity<String> deleteDebtor(@PathVariable Long debtorId) {
        logger.info("Deleting debtor with ID: {}", debtorId);

        try {
            debtorService.delete(debtorId);
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
