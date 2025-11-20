package com.kraya.platform.service.impl;

import com.kraya.platform.dto.DebtTransferRequest;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Debt;
import com.kraya.platform.model.DebtTransfer;
import com.kraya.platform.repository.DebtRepository;
import com.kraya.platform.repository.DebtTransferRepository;
import com.kraya.platform.repository.UserRepository;
import com.kraya.platform.service.DebtTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DebtTransferServiceImpl provides the implementation for debt transfer-related operations.
 */
@Service
@Transactional
public class DebtTransferServiceImpl implements DebtTransferService {

    private static final Logger logger = LoggerFactory.getLogger(DebtTransferServiceImpl.class);

    private final DebtTransferRepository debtTransferRepository;
    private final DebtRepository debtRepository;
    private final UserRepository userRepository;

    @Autowired
    public DebtTransferServiceImpl(DebtTransferRepository debtTransferRepository,
                                   DebtRepository debtRepository,
                                   UserRepository userRepository) {
        this.debtTransferRepository = debtTransferRepository;
        this.debtRepository = debtRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<DebtTransfer> findAll() {
        logger.info("Fetching all debt transfers");
        return debtTransferRepository.findAll();
    }

    @Override
    public DebtTransfer findById(Long id) {
        logger.info("Fetching debt transfer with ID: {}", id);
        return debtTransferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Debt transfer not found with ID: " + id));
    }

    @Override
    public DebtTransfer create(DebtTransferRequest request) {
        logger.info("Creating new debt transfer for debt ID: {} from creditor ID: {} to creditor ID: {}",
                request.getDebtId(), request.getFromCreditorId(), request.getToCreditorId());

        // Validate debt exists
        Debt debt = debtRepository.findById(request.getDebtId())
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found with ID: " + request.getDebtId()));

        // Validate from creditor exists
        AppUser fromCreditor = (AppUser) userRepository.findById(request.getFromCreditorId())
                .orElseThrow(() -> new ResourceNotFoundException("From creditor not found with ID: " + request.getFromCreditorId()));

        // Validate to creditor exists
        AppUser toCreditor = (AppUser) userRepository.findById(request.getToCreditorId())
                .orElseThrow(() -> new ResourceNotFoundException("To creditor not found with ID: " + request.getToCreditorId()));

        DebtTransfer debtTransfer = new DebtTransfer();
        debtTransfer.setDebt(debt);
        debtTransfer.setFromCreditor(fromCreditor);
        debtTransfer.setToCreditor(toCreditor);
        debtTransfer.setTransferDate(LocalDateTime.now());
        debtTransfer.setAmount(request.getAmount());
        debtTransfer.setStatus(request.getStatus());

        return debtTransferRepository.save(debtTransfer);
    }

    @Override
    public DebtTransfer update(Long id, DebtTransferRequest request) {
        logger.info("Updating debt transfer with ID: {}", id);

        DebtTransfer debtTransfer = findById(id);

        // Update debt if changed
        if (!debtTransfer.getDebt().getDebtId().equals(request.getDebtId())) {
            Debt debt = debtRepository.findById(request.getDebtId())
                    .orElseThrow(() -> new ResourceNotFoundException("Debt not found with ID: " + request.getDebtId()));
            debtTransfer.setDebt(debt);
        }

        // Update from creditor if changed
        if (!debtTransfer.getFromCreditor().getUserId().equals(request.getFromCreditorId())) {
            AppUser fromCreditor = (AppUser) userRepository.findById(request.getFromCreditorId())
                    .orElseThrow(() -> new ResourceNotFoundException("From creditor not found with ID: " + request.getFromCreditorId()));
            debtTransfer.setFromCreditor(fromCreditor);
        }

        // Update to creditor if changed
        if (!debtTransfer.getToCreditor().getUserId().equals(request.getToCreditorId())) {
            AppUser toCreditor = (AppUser) userRepository.findById(request.getToCreditorId())
                    .orElseThrow(() -> new ResourceNotFoundException("To creditor not found with ID: " + request.getToCreditorId()));
            debtTransfer.setToCreditor(toCreditor);
        }

        debtTransfer.setAmount(request.getAmount());
        debtTransfer.setStatus(request.getStatus());

        return debtTransferRepository.save(debtTransfer);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting debt transfer with ID: {}", id);
        DebtTransfer debtTransfer = findById(id);
        debtTransferRepository.delete(debtTransfer);
    }

    @Override
    public List<DebtTransfer> findByDebtId(Long debtId) {
        logger.info("Fetching debt transfers for debt ID: {}", debtId);
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found with ID: " + debtId));
        return debtTransferRepository.findByDebt(debt);
    }

    @Override
    public List<DebtTransfer> findByFromCreditorId(Long fromCreditorId) {
        logger.info("Fetching debt transfers from creditor ID: {}", fromCreditorId);
        AppUser fromCreditor = (AppUser) userRepository.findById(fromCreditorId)
                .orElseThrow(() -> new ResourceNotFoundException("From creditor not found with ID: " + fromCreditorId));
        return debtTransferRepository.findByFromCreditor(fromCreditor);
    }

    @Override
    public List<DebtTransfer> findByToCreditorId(Long toCreditorId) {
        logger.info("Fetching debt transfers to creditor ID: {}", toCreditorId);
        AppUser toCreditor = (AppUser) userRepository.findById(toCreditorId)
                .orElseThrow(() -> new ResourceNotFoundException("To creditor not found with ID: " + toCreditorId));
        return debtTransferRepository.findByToCreditor(toCreditor);
    }

    @Override
    public List<DebtTransfer> findByStatus(String status) {
        logger.info("Fetching debt transfers with status: {}", status);
        return debtTransferRepository.findByStatus(status);
    }
}
