package com.kraya.platform.service.impl;

import com.kraya.platform.dto.PaymentRequest;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Debt;
import com.kraya.platform.model.Payment;
import com.kraya.platform.repository.DebtRepository;
import com.kraya.platform.repository.PaymentRepository;
import com.kraya.platform.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * PaymentServiceImpl provides the implementation for payment-related operations.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final DebtRepository debtRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, DebtRepository debtRepository) {
        this.paymentRepository = paymentRepository;
        this.debtRepository = debtRepository;
    }

    @Override
    public List<Payment> findAll() {
        logger.info("Fetching all payments");
        return paymentRepository.findAll();
    }

    @Override
    public Payment findById(Long id) {
        logger.info("Fetching payment with ID: {}", id);
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
    }

    @Override
    public Payment create(PaymentRequest request) {
        logger.info("Creating new payment for debt ID: {}", request.getDebtId());

        // Validate debt exists
        Debt debt = debtRepository.findById(request.getDebtId())
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found with ID: " + request.getDebtId()));

        // Validate payment amount doesn't exceed current debt
        if (request.getAmount().compareTo(debt.getCurrentAmount()) > 0) {
            throw new IllegalArgumentException("Payment amount exceeds current debt amount");
        }

        Payment payment = new Payment();
        payment.setDebt(debt);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionDate(LocalDateTime.now());

        // Update debt current amount
        BigDecimal newAmount = debt.getCurrentAmount().subtract(request.getAmount());
        debt.setCurrentAmount(newAmount);

        // Update debt status if fully paid
        if (newAmount.compareTo(BigDecimal.ZERO) == 0) {
            debt.setStatus("PAID");
        }

        debtRepository.save(debt);
        return paymentRepository.save(payment);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting payment with ID: {}", id);
        Payment payment = findById(id);
        paymentRepository.delete(payment);
    }

    @Override
    public List<Payment> findByDebtId(Long debtId) {
        logger.info("Fetching payments for debt ID: {}", debtId);
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found with ID: " + debtId));
        return paymentRepository.findByDebtOrderByTransactionDateDesc(debt);
    }
}
