package com.kraya.platform.controller;

import com.kraya.platform.dto.PaymentRequest;
import com.kraya.platform.dto.PaymentResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Payment;
import com.kraya.platform.service.PaymentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PaymentController handles payment-related HTTP requests, providing endpoints
 * for payment creation, retrieval, and deletion.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Creates a new payment.
     *
     * @param request the payment creation request
     * @return ResponseEntity with payment response and created status
     */
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
        logger.info("Received request to create payment for debt ID: {}", request.getDebtId());

        try {
            Payment payment = paymentService.create(request);
            PaymentResponse response = new PaymentResponse(payment.getPaymentId(), "Payment processed successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Payment creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PaymentResponse(e.getMessage()));
        } catch (IllegalArgumentException e) {
            logger.error("Payment creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PaymentResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all payments.
     *
     * @return ResponseEntity containing the list of payments
     */
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        logger.info("Fetching all payments");
        List<Payment> payments = paymentService.findAll();
        return ResponseEntity.ok(payments);
    }

    /**
     * Retrieves a payment by ID.
     *
     * @param paymentId the ID of the payment to retrieve
     * @return ResponseEntity containing the payment data if found
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long paymentId) {
        logger.info("Fetching payment with ID: {}", paymentId);
        try {
            Payment payment = paymentService.findById(paymentId);
            return ResponseEntity.ok(payment);
        } catch (ResourceNotFoundException e) {
            logger.error("Payment not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all payments for a specific debt.
     *
     * @param debtId the debt ID
     * @return ResponseEntity containing the list of payments
     */
    @GetMapping("/debt/{debtId}")
    public ResponseEntity<List<Payment>> getPaymentsByDebtId(@PathVariable Long debtId) {
        logger.info("Fetching payments for debt ID: {}", debtId);
        try {
            List<Payment> payments = paymentService.findByDebtId(debtId);
            return ResponseEntity.ok(payments);
        } catch (ResourceNotFoundException e) {
            logger.error("Debt not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a payment by ID.
     *
     * @param paymentId the ID of the payment to delete
     * @return ResponseEntity with No Content status if deletion is successful
     */
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> deletePayment(@PathVariable Long paymentId) {
        logger.info("Deleting payment with ID: {}", paymentId);

        try {
            paymentService.delete(paymentId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PaymentResponse(e.getMessage()));
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
