package com.kraya.platform.controller;

import com.kraya.platform.dto.PaymentPlanRequest;
import com.kraya.platform.dto.PaymentPlanResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.PaymentPlan;
import com.kraya.platform.service.PaymentPlanService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PaymentPlanController handles payment plan-related HTTP requests, providing endpoints
 * for payment plan creation, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/payment-plans")
public class PaymentPlanController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentPlanController.class);

    private final PaymentPlanService paymentPlanService;

    @Autowired
    public PaymentPlanController(PaymentPlanService paymentPlanService) {
        this.paymentPlanService = paymentPlanService;
    }

    /**
     * Creates a new payment plan.
     *
     * @param request the payment plan creation request
     * @return ResponseEntity with payment plan response and created status
     */
    @PostMapping
    public ResponseEntity<PaymentPlanResponse> createPaymentPlan(@Valid @RequestBody PaymentPlanRequest request) {
        logger.info("Received request to create payment plan for debt ID: {}", request.getDebtId());

        try {
            PaymentPlan paymentPlan = paymentPlanService.create(request);
            PaymentPlanResponse response = new PaymentPlanResponse(paymentPlan.getPlanId(), "Payment plan created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PaymentPlanResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PaymentPlanResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all payment plans.
     *
     * @return ResponseEntity containing the list of payment plans
     */
    @GetMapping
    public ResponseEntity<List<PaymentPlan>> getAllPaymentPlans() {
        logger.info("Fetching all payment plans");
        List<PaymentPlan> paymentPlans = paymentPlanService.findAll();
        return ResponseEntity.ok(paymentPlans);
    }

    /**
     * Retrieves a payment plan by ID.
     *
     * @param planId the ID of the payment plan to retrieve
     * @return ResponseEntity containing the payment plan data if found
     */
    @GetMapping("/{planId}")
    public ResponseEntity<PaymentPlan> getPaymentPlanById(@PathVariable Long planId) {
        logger.info("Fetching payment plan with ID: {}", planId);
        try {
            PaymentPlan paymentPlan = paymentPlanService.findById(planId);
            return ResponseEntity.ok(paymentPlan);
        } catch (ResourceNotFoundException e) {
            logger.error("Payment plan not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all payment plans for a specific debt.
     *
     * @param debtId the debt ID
     * @return ResponseEntity containing the list of payment plans
     */
    @GetMapping("/debt/{debtId}")
    public ResponseEntity<List<PaymentPlan>> getPaymentPlansByDebtId(@PathVariable Long debtId) {
        logger.info("Fetching payment plans for debt ID: {}", debtId);
        try {
            List<PaymentPlan> paymentPlans = paymentPlanService.findByDebtId(debtId);
            return ResponseEntity.ok(paymentPlans);
        } catch (ResourceNotFoundException e) {
            logger.error("Debt not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all payment plans for a specific creditor.
     *
     * @param creditorId the creditor ID
     * @return ResponseEntity containing the list of payment plans
     */
    @GetMapping("/creditor/{creditorId}")
    public ResponseEntity<List<PaymentPlan>> getPaymentPlansByCreditorId(@PathVariable Long creditorId) {
        logger.info("Fetching payment plans for creditor ID: {}", creditorId);
        try {
            List<PaymentPlan> paymentPlans = paymentPlanService.findByCreditorId(creditorId);
            return ResponseEntity.ok(paymentPlans);
        } catch (ResourceNotFoundException e) {
            logger.error("Creditor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates payment plan information.
     *
     * @param planId  the ID of the payment plan to update
     * @param request the payment plan update request
     * @return ResponseEntity with a success message
     */
    @PutMapping("/{planId}")
    public ResponseEntity<PaymentPlanResponse> updatePaymentPlan(@PathVariable Long planId, @Valid @RequestBody PaymentPlanRequest request) {
        logger.info("Updating payment plan with ID: {}", planId);

        try {
            paymentPlanService.update(planId, request);
            return ResponseEntity.ok(new PaymentPlanResponse(planId, "Payment plan updated successfully"));
        } catch (ResourceNotFoundException e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PaymentPlanResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PaymentPlanResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a payment plan by ID.
     *
     * @param planId the ID of the payment plan to delete
     * @return ResponseEntity with No Content status if deletion is successful
     */
    @DeleteMapping("/{planId}")
    public ResponseEntity<PaymentPlanResponse> deletePaymentPlan(@PathVariable Long planId) {
        logger.info("Deleting payment plan with ID: {}", planId);

        try {
            paymentPlanService.delete(planId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PaymentPlanResponse(e.getMessage()));
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
