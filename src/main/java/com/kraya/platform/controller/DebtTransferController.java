package com.kraya.platform.controller;

import com.kraya.platform.dto.DebtTransferRequest;
import com.kraya.platform.dto.DebtTransferResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.DebtTransfer;
import com.kraya.platform.service.DebtTransferService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DebtTransferController handles debt transfer-related HTTP requests, providing endpoints
 * for debt transfer creation, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/debt-transfers")
public class DebtTransferController {

    private static final Logger logger = LoggerFactory.getLogger(DebtTransferController.class);

    private final DebtTransferService debtTransferService;

    @Autowired
    public DebtTransferController(DebtTransferService debtTransferService) {
        this.debtTransferService = debtTransferService;
    }

    /**
     * Creates a new debt transfer.
     *
     * @param request the debt transfer creation request
     * @return ResponseEntity with debt transfer response and created status
     */
    @PostMapping
    public ResponseEntity<DebtTransferResponse> createDebtTransfer(@Valid @RequestBody DebtTransferRequest request) {
        logger.info("Received request to create debt transfer for debt ID: {}", request.getDebtId());

        try {
            DebtTransfer debtTransfer = debtTransferService.create(request);
            DebtTransferResponse response = new DebtTransferResponse(debtTransfer.getTransferId(), "Debt transfer created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DebtTransferResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DebtTransferResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all debt transfers.
     *
     * @return ResponseEntity containing the list of debt transfers
     */
    @GetMapping
    public ResponseEntity<List<DebtTransfer>> getAllDebtTransfers() {
        logger.info("Fetching all debt transfers");
        List<DebtTransfer> debtTransfers = debtTransferService.findAll();
        return ResponseEntity.ok(debtTransfers);
    }

    /**
     * Retrieves a debt transfer by ID.
     *
     * @param transferId the ID of the debt transfer to retrieve
     * @return ResponseEntity containing the debt transfer data if found
     */
    @GetMapping("/{transferId}")
    public ResponseEntity<DebtTransfer> getDebtTransferById(@PathVariable Long transferId) {
        logger.info("Fetching debt transfer with ID: {}", transferId);
        try {
            DebtTransfer debtTransfer = debtTransferService.findById(transferId);
            return ResponseEntity.ok(debtTransfer);
        } catch (ResourceNotFoundException e) {
            logger.error("Debt transfer not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all debt transfers for a specific debt.
     *
     * @param debtId the debt ID
     * @return ResponseEntity containing the list of debt transfers
     */
    @GetMapping("/debt/{debtId}")
    public ResponseEntity<List<DebtTransfer>> getDebtTransfersByDebtId(@PathVariable Long debtId) {
        logger.info("Fetching debt transfers for debt ID: {}", debtId);
        try {
            List<DebtTransfer> debtTransfers = debtTransferService.findByDebtId(debtId);
            return ResponseEntity.ok(debtTransfers);
        } catch (ResourceNotFoundException e) {
            logger.error("Debt not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all debt transfers from a specific creditor.
     *
     * @param creditorId the from creditor ID
     * @return ResponseEntity containing the list of debt transfers
     */
    @GetMapping("/from-creditor/{creditorId}")
    public ResponseEntity<List<DebtTransfer>> getDebtTransfersByFromCreditorId(@PathVariable Long creditorId) {
        logger.info("Fetching debt transfers from creditor ID: {}", creditorId);
        try {
            List<DebtTransfer> debtTransfers = debtTransferService.findByFromCreditorId(creditorId);
            return ResponseEntity.ok(debtTransfers);
        } catch (ResourceNotFoundException e) {
            logger.error("From creditor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all debt transfers to a specific creditor.
     *
     * @param creditorId the to creditor ID
     * @return ResponseEntity containing the list of debt transfers
     */
    @GetMapping("/to-creditor/{creditorId}")
    public ResponseEntity<List<DebtTransfer>> getDebtTransfersByToCreditorId(@PathVariable Long creditorId) {
        logger.info("Fetching debt transfers to creditor ID: {}", creditorId);
        try {
            List<DebtTransfer> debtTransfers = debtTransferService.findByToCreditorId(creditorId);
            return ResponseEntity.ok(debtTransfers);
        } catch (ResourceNotFoundException e) {
            logger.error("To creditor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all debt transfers by status.
     *
     * @param status the transfer status
     * @return ResponseEntity containing the list of debt transfers
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<DebtTransfer>> getDebtTransfersByStatus(@PathVariable String status) {
        logger.info("Fetching debt transfers with status: {}", status);
        List<DebtTransfer> debtTransfers = debtTransferService.findByStatus(status);
        return ResponseEntity.ok(debtTransfers);
    }

    /**
     * Updates debt transfer information.
     *
     * @param transferId  the ID of the debt transfer to update
     * @param request the debt transfer update request
     * @return ResponseEntity with a success message
     */
    @PutMapping("/{transferId}")
    public ResponseEntity<DebtTransferResponse> updateDebtTransfer(@PathVariable Long transferId, @Valid @RequestBody DebtTransferRequest request) {
        logger.info("Updating debt transfer with ID: {}", transferId);

        try {
            debtTransferService.update(transferId, request);
            return ResponseEntity.ok(new DebtTransferResponse(transferId, "Debt transfer updated successfully"));
        } catch (ResourceNotFoundException e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DebtTransferResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DebtTransferResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a debt transfer by ID.
     *
     * @param transferId the ID of the debt transfer to delete
     * @return ResponseEntity with No Content status if deletion is successful
     */
    @DeleteMapping("/{transferId}")
    public ResponseEntity<DebtTransferResponse> deleteDebtTransfer(@PathVariable Long transferId) {
        logger.info("Deleting debt transfer with ID: {}", transferId);

        try {
            debtTransferService.delete(transferId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DebtTransferResponse(e.getMessage()));
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
