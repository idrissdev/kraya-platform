package com.kraya.platform.controller;

import com.kraya.platform.dto.DebtRequest;
import com.kraya.platform.dto.DebtResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Debt;
import com.kraya.platform.service.DebtService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DebtController handles debt-related HTTP requests, providing endpoints
 * for debt creation, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/debts")
public class DebtController {

    private static final Logger logger = LoggerFactory.getLogger(DebtController.class);

    private final DebtService debtService;

    @Autowired
    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    /**
     * Creates a new debt.
     *
     * @param request the debt creation request
     * @return ResponseEntity with debt response and created status
     */
    @PostMapping
    public ResponseEntity<DebtResponse> createDebt(@Valid @RequestBody DebtRequest request) {
        logger.info("Received request to create debt for debtor ID: {}", request.getDebtorId());

        try {
            Debt debt = debtService.create(request);
            DebtResponse response = new DebtResponse(debt.getDebtId(), "Debt created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DebtResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DebtResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all debts.
     *
     * @return ResponseEntity containing the list of debts
     */
    @GetMapping
    public ResponseEntity<List<Debt>> getAllDebts() {
        logger.info("Fetching all debts");
        List<Debt> debts = debtService.findAll();
        return ResponseEntity.ok(debts);
    }

    /**
     * Retrieves a debt by ID.
     *
     * @param debtId the ID of the debt to retrieve
     * @return ResponseEntity containing the debt data if found
     */
    @GetMapping("/{debtId}")
    public ResponseEntity<Debt> getDebtById(@PathVariable Long debtId) {
        logger.info("Fetching debt with ID: {}", debtId);
        try {
            Debt debt = debtService.findById(debtId);
            return ResponseEntity.ok(debt);
        } catch (ResourceNotFoundException e) {
            logger.error("Debt not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all debts for a specific debtor.
     *
     * @param debtorId the debtor ID
     * @return ResponseEntity containing the list of debts
     */
    @GetMapping("/debtor/{debtorId}")
    public ResponseEntity<List<Debt>> getDebtsByDebtorId(@PathVariable Long debtorId) {
        logger.info("Fetching debts for debtor ID: {}", debtorId);
        try {
            List<Debt> debts = debtService.findByDebtorId(debtorId);
            return ResponseEntity.ok(debts);
        } catch (ResourceNotFoundException e) {
            logger.error("Debtor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all debts for a specific creditor.
     *
     * @param creditorId the creditor ID
     * @return ResponseEntity containing the list of debts
     */
    @GetMapping("/creditor/{creditorId}")
    public ResponseEntity<List<Debt>> getDebtsByCreditorId(@PathVariable Long creditorId) {
        logger.info("Fetching debts for creditor ID: {}", creditorId);
        try {
            List<Debt> debts = debtService.findByCreditorId(creditorId);
            return ResponseEntity.ok(debts);
        } catch (ResourceNotFoundException e) {
            logger.error("Creditor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all debts by status.
     *
     * @param status the debt status
     * @return ResponseEntity containing the list of debts
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Debt>> getDebtsByStatus(@PathVariable String status) {
        logger.info("Fetching debts with status: {}", status);
        List<Debt> debts = debtService.findByStatus(status);
        return ResponseEntity.ok(debts);
    }

    /**
     * Updates debt information.
     *
     * @param debtId  the ID of the debt to update
     * @param request the debt update request
     * @return ResponseEntity with a success message
     */
    @PutMapping("/{debtId}")
    public ResponseEntity<DebtResponse> updateDebt(@PathVariable Long debtId, @Valid @RequestBody DebtRequest request) {
        logger.info("Updating debt with ID: {}", debtId);

        try {
            debtService.update(debtId, request);
            return ResponseEntity.ok(new DebtResponse(debtId, "Debt updated successfully"));
        } catch (ResourceNotFoundException e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DebtResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DebtResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a debt by ID.
     *
     * @param debtId the ID of the debt to delete
     * @return ResponseEntity with No Content status if deletion is successful
     */
    @DeleteMapping("/{debtId}")
    public ResponseEntity<DebtResponse> deleteDebt(@PathVariable Long debtId) {
        logger.info("Deleting debt with ID: {}", debtId);

        try {
            debtService.delete(debtId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DebtResponse(e.getMessage()));
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
