package com.kraya.platform.service.impl;

import com.kraya.platform.dto.PaymentPlanRequest;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Debt;
import com.kraya.platform.model.PaymentPlan;
import com.kraya.platform.repository.DebtRepository;
import com.kraya.platform.repository.PaymentPlanRepository;
import com.kraya.platform.repository.UserRepository;
import com.kraya.platform.service.PaymentPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * PaymentPlanServiceImpl provides the implementation for payment plan-related operations.
 */
@Service
@Transactional
public class PaymentPlanServiceImpl implements PaymentPlanService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentPlanServiceImpl.class);

    private final PaymentPlanRepository paymentPlanRepository;
    private final DebtRepository debtRepository;
    private final UserRepository userRepository;

    @Autowired
    public PaymentPlanServiceImpl(PaymentPlanRepository paymentPlanRepository,
                                  DebtRepository debtRepository,
                                  UserRepository userRepository) {
        this.paymentPlanRepository = paymentPlanRepository;
        this.debtRepository = debtRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PaymentPlan> findAll() {
        logger.info("Fetching all payment plans");
        return paymentPlanRepository.findAll();
    }

    @Override
    public PaymentPlan findById(Long id) {
        logger.info("Fetching payment plan with ID: {}", id);
        return paymentPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment plan not found with ID: " + id));
    }

    @Override
    public PaymentPlan create(PaymentPlanRequest request) {
        logger.info("Creating new payment plan for debt ID: {}", request.getDebtId());

        // Validate debt exists
        Debt debt = debtRepository.findById(request.getDebtId())
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found with ID: " + request.getDebtId()));

        // Validate creditor exists
        AppUser creditor = (AppUser) userRepository.findById(request.getCreditorId())
                .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + request.getCreditorId()));

        PaymentPlan paymentPlan = new PaymentPlan();
        paymentPlan.setDebt(debt);
        paymentPlan.setCreditor(creditor);
        paymentPlan.setPlanType(request.getPlanType());
        paymentPlan.setInstallmentAmount(request.getInstallmentAmount());
        paymentPlan.setStartDate(request.getStartDate());
        paymentPlan.setEndDate(request.getEndDate());
        paymentPlan.setStatus(request.getStatus());

        return paymentPlanRepository.save(paymentPlan);
    }

    @Override
    public PaymentPlan update(Long id, PaymentPlanRequest request) {
        logger.info("Updating payment plan with ID: {}", id);

        PaymentPlan paymentPlan = findById(id);

        // Update debt if changed
        if (!paymentPlan.getDebt().getDebtId().equals(request.getDebtId())) {
            Debt debt = debtRepository.findById(request.getDebtId())
                    .orElseThrow(() -> new ResourceNotFoundException("Debt not found with ID: " + request.getDebtId()));
            paymentPlan.setDebt(debt);
        }

        // Update creditor if changed
        if (!paymentPlan.getCreditor().getUserId().equals(request.getCreditorId())) {
            AppUser creditor = (AppUser) userRepository.findById(request.getCreditorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + request.getCreditorId()));
            paymentPlan.setCreditor(creditor);
        }

        paymentPlan.setPlanType(request.getPlanType());
        paymentPlan.setInstallmentAmount(request.getInstallmentAmount());
        paymentPlan.setStartDate(request.getStartDate());
        paymentPlan.setEndDate(request.getEndDate());
        paymentPlan.setStatus(request.getStatus());

        return paymentPlanRepository.save(paymentPlan);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting payment plan with ID: {}", id);
        PaymentPlan paymentPlan = findById(id);
        paymentPlanRepository.delete(paymentPlan);
    }

    @Override
    public List<PaymentPlan> findByDebtId(Long debtId) {
        logger.info("Fetching payment plans for debt ID: {}", debtId);
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found with ID: " + debtId));
        return paymentPlanRepository.findByDebt(debt);
    }

    @Override
    public List<PaymentPlan> findByCreditorId(Long creditorId) {
        logger.info("Fetching payment plans for creditor ID: {}", creditorId);
        AppUser creditor = (AppUser) userRepository.findById(creditorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + creditorId));
        return paymentPlanRepository.findByCreditor(creditor);
    }
}
