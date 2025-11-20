package com.kraya.platform.service.impl;

import com.kraya.platform.dto.DebtRequest;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Debt;
import com.kraya.platform.repository.DebtRepository;
import com.kraya.platform.repository.UserRepository;
import com.kraya.platform.service.DebtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DebtServiceImpl provides the implementation for debt-related operations.
 */
@Service
@Transactional
public class DebtServiceImpl implements DebtService {

    private static final Logger logger = LoggerFactory.getLogger(DebtServiceImpl.class);

    private final DebtRepository debtRepository;
    private final UserRepository userRepository;

    @Autowired
    public DebtServiceImpl(DebtRepository debtRepository, UserRepository userRepository) {
        this.debtRepository = debtRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Debt> findAll() {
        logger.info("Fetching all debts");
        return debtRepository.findAll();
    }

    @Override
    public Debt findById(Long id) {
        logger.info("Fetching debt with ID: {}", id);
        return debtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found with ID: " + id));
    }

    @Override
    public Debt create(DebtRequest request) {
        logger.info("Creating new debt for debtor ID: {} and creditor ID: {}",
                request.getDebtorId(), request.getCreditorId());

        // Validate debtor and creditor exist
        AppUser debtor = (AppUser) userRepository.findById(request.getDebtorId())
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + request.getDebtorId()));

        AppUser creditor = (AppUser) userRepository.findById(request.getCreditorId())
                .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + request.getCreditorId()));

        Debt debt = new Debt();
        debt.setDebtor(debtor);
        debt.setCreditor(creditor);
        debt.setOriginalAmount(request.getOriginalAmount());
        debt.setCurrentAmount(request.getCurrentAmount());
        debt.setInterestRate(request.getInterestRate());
        debt.setDueDate(request.getDueDate());
        debt.setStatus(request.getStatus());
        debt.setCreationDate(LocalDateTime.now());

        return debtRepository.save(debt);
    }

    @Override
    public Debt update(Long id, DebtRequest request) {
        logger.info("Updating debt with ID: {}", id);

        Debt debt = findById(id);

        // Update debtor and creditor if changed
        if (!debt.getDebtor().getUserId().equals(request.getDebtorId())) {
            AppUser debtor = (AppUser) userRepository.findById(request.getDebtorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + request.getDebtorId()));
            debt.setDebtor(debtor);
        }

        if (!debt.getCreditor().getUserId().equals(request.getCreditorId())) {
            AppUser creditor = (AppUser) userRepository.findById(request.getCreditorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + request.getCreditorId()));
            debt.setCreditor(creditor);
        }

        debt.setOriginalAmount(request.getOriginalAmount());
        debt.setCurrentAmount(request.getCurrentAmount());
        debt.setInterestRate(request.getInterestRate());
        debt.setDueDate(request.getDueDate());
        debt.setStatus(request.getStatus());

        return debtRepository.save(debt);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting debt with ID: {}", id);
        Debt debt = findById(id);
        debtRepository.delete(debt);
    }

    @Override
    public List<Debt> findByDebtorId(Long debtorId) {
        logger.info("Fetching debts for debtor ID: {}", debtorId);
        AppUser debtor = (AppUser) userRepository.findById(debtorId)
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + debtorId));
        return debtRepository.findByDebtor(debtor);
    }

    @Override
    public List<Debt> findByCreditorId(Long creditorId) {
        logger.info("Fetching debts for creditor ID: {}", creditorId);
        AppUser creditor = (AppUser) userRepository.findById(creditorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + creditorId));
        return debtRepository.findByCreditor(creditor);
    }

    @Override
    public List<Debt> findByStatus(String status) {
        logger.info("Fetching debts with status: {}", status);
        return debtRepository.findByStatus(status);
    }
}
