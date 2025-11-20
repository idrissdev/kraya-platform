package com.kraya.platform.service;

import com.kraya.platform.dto.PaymentPlanRequest;
import com.kraya.platform.model.PaymentPlan;

import java.util.List;

/**
 * PaymentPlanService interface defines the contract for payment plan-related operations.
 */
public interface PaymentPlanService {

    /**
     * Retrieves all payment plans.
     *
     * @return a list of payment plans
     */
    List<PaymentPlan> findAll();

    /**
     * Retrieves a payment plan by ID.
     *
     * @param id the ID of the payment plan to retrieve
     * @return the payment plan object if found
     */
    PaymentPlan findById(Long id);

    /**
     * Creates a new payment plan based on the request.
     *
     * @param request the payment plan request containing necessary data
     * @return the created payment plan
     */
    PaymentPlan create(PaymentPlanRequest request);

    /**
     * Updates an existing payment plan's information.
     *
     * @param id      the ID of the payment plan to update
     * @param request the updated payment plan data
     * @return the updated payment plan
     */
    PaymentPlan update(Long id, PaymentPlanRequest request);

    /**
     * Deletes a payment plan by ID.
     *
     * @param id the ID of the payment plan to delete
     */
    void delete(Long id);

    /**
     * Finds all payment plans for a specific debt.
     *
     * @param debtId the debt ID
     * @return list of payment plans
     */
    List<PaymentPlan> findByDebtId(Long debtId);

    /**
     * Finds all payment plans created by a specific creditor.
     *
     * @param creditorId the creditor ID
     * @return list of payment plans
     */
    List<PaymentPlan> findByCreditorId(Long creditorId);
}
