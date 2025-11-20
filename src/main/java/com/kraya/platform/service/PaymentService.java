package com.kraya.platform.service;

import com.kraya.platform.dto.PaymentRequest;
import com.kraya.platform.model.Payment;

import java.util.List;

/**
 * PaymentService interface defines the contract for payment-related operations.
 */
public interface PaymentService {

    /**
     * Retrieves all payments.
     *
     * @return a list of payments
     */
    List<Payment> findAll();

    /**
     * Retrieves a payment by ID.
     *
     * @param id the ID of the payment to retrieve
     * @return the payment object if found
     */
    Payment findById(Long id);

    /**
     * Creates a new payment and updates the debt amount.
     *
     * @param request the payment request containing necessary data
     * @return the created payment
     */
    Payment create(PaymentRequest request);

    /**
     * Deletes a payment by ID.
     *
     * @param id the ID of the payment to delete
     */
    void delete(Long id);

    /**
     * Finds all payments for a specific debt.
     *
     * @param debtId the debt ID
     * @return list of payments
     */
    List<Payment> findByDebtId(Long debtId);
}
