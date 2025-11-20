package com.kraya.platform.controller;

import com.kraya.platform.dto.CreditorRequest;
import com.kraya.platform.dto.CreditorResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Creditor;
import com.kraya.platform.service.CreditorService;
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
 * CreditorController handles creditor-related HTTP requests, providing endpoints
 * for creditor registration, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/creditors")
public class CreditorController {

    private static final Logger logger = LoggerFactory.getLogger(CreditorController.class);

    private final CreditorService creditorService;

    @Autowired
    public CreditorController(CreditorService creditorService) {
        this.creditorService = creditorService;
    }

    /**
     * Registers a new creditor.
     *
     * @param request the creditor registration request containing necessary data
     * @return ResponseEntity with creditor response and created status
     */
    @PostMapping
    public ResponseEntity<CreditorResponse> createCreditor(@Valid @RequestBody CreditorRequest request) {
        logger.info("Received registration request for creditor with username: {}", request.getUsername());

        try {
            Creditor createdCreditor = creditorService.create(request);
            CreditorResponse response = creditorService.toResponse(createdCreditor);
            response.setMessage("Creditor registered successfully");

            return ResponseEntity
                    .created(URI.create("/api/creditors/" + createdCreditor.getUserId()))
                    .body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CreditorResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all creditors.
     *
     * @return ResponseEntity containing list of all creditors
     */
    @GetMapping
    public ResponseEntity<List<CreditorResponse>> getAllCreditors() {
        logger.info("Fetching all creditors");
        List<Creditor> creditors = creditorService.findAll();
        List<CreditorResponse> responses = creditors.stream()
                .map(creditorService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves a creditor by ID.
     *
     * @param creditorId the ID of the creditor to retrieve
     * @return ResponseEntity containing the creditor data if found, otherwise Not Found status
     */
    @GetMapping("/{creditorId}")
    public ResponseEntity<CreditorResponse> getCreditorById(@PathVariable Long creditorId) {
        logger.info("Fetching creditor with ID: {}", creditorId);

        try {
            Creditor creditor = creditorService.findById(creditorId);
            CreditorResponse response = creditorService.toResponse(creditor);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Creditor not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CreditorResponse(e.getMessage()));
        }
    }

    /**
     * Updates creditor information.
     *
     * @param creditorId the ID of the creditor to update
     * @param request    the creditor update request containing updated data
     * @return ResponseEntity with a success message and OK status if update is successful, otherwise Not Found status
     */
    @PutMapping("/{creditorId}")
    public ResponseEntity<CreditorResponse> updateCreditor(@PathVariable Long creditorId,
                                                            @Valid @RequestBody CreditorRequest request) {
        logger.info("Updating creditor with ID: {}", creditorId);

        try {
            Creditor updatedCreditor = creditorService.update(creditorId, request);
            CreditorResponse response = creditorService.toResponse(updatedCreditor);
            response.setMessage("Creditor updated successfully");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CreditorResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a creditor by ID.
     *
     * @param creditorId the ID of the creditor to delete
     * @return ResponseEntity with No Content status if deletion is successful, otherwise Not Found status
     */
    @DeleteMapping("/{creditorId}")
    public ResponseEntity<String> deleteCreditor(@PathVariable Long creditorId) {
        logger.info("Deleting creditor with ID: {}", creditorId);

        try {
            creditorService.delete(creditorId);
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
