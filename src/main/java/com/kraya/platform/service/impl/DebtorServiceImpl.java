package com.kraya.platform.service.impl;

import com.kraya.platform.dto.DebtorRequest;
import com.kraya.platform.dto.DebtorResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Debtor;
import com.kraya.platform.model.User;
import com.kraya.platform.repository.DebtorRepository;
import com.kraya.platform.service.DebtorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DebtorServiceImpl provides the implementation for debtor-related operations.
 */
@Service
@Transactional
public class DebtorServiceImpl implements DebtorService {

    private static final Logger logger = LoggerFactory.getLogger(DebtorServiceImpl.class);

    private final DebtorRepository debtorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DebtorServiceImpl(DebtorRepository debtorRepository, PasswordEncoder passwordEncoder) {
        this.debtorRepository = debtorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Debtor> findAll() {
        logger.info("Retrieving all debtors");
        return debtorRepository.findAll();
    }

    @Override
    public Debtor findById(Long id) {
        logger.info("Retrieving debtor with ID: {}", id);
        return debtorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + id));
    }

    @Override
    public Debtor create(DebtorRequest request) {
        logger.info("Creating new debtor with username: {}", request.getUsername());

        // Validate if the debtor already exists
        debtorRepository.findByEmail(request.getEmail()).ifPresent(debtor -> {
            throw new IllegalArgumentException("Email already exists.");
        });

        Debtor debtor = new Debtor();

        // Set User fields
        debtor.setUsername(request.getUsername());
        debtor.setPassword(passwordEncoder.encode(request.getPassword()));
        debtor.setEmail(request.getEmail());
        debtor.setFirstName(request.getFirstName());
        debtor.setLastName(request.getLastName());
        debtor.setProfilePictureUrl(request.getProfilePictureUrl());
        debtor.setStatus(User.Status.ACTIVE);

        // Set AppUser fields
        debtor.setRegistrationDate(LocalDateTime.now());

        // Set Debtor-specific fields
        debtor.setPhoneNumber(request.getPhoneNumber());
        debtor.setAddress(request.getAddress());
        debtor.setDateOfBirth(request.getDateOfBirth());
        debtor.setIncomeLevel(request.getIncomeLevel());
        debtor.setEmploymentStatus(request.getEmploymentStatus());
        debtor.setDebtReason(request.getDebtReason());
        debtor.setMaritalStatus(request.getMaritalStatus());
        debtor.setDependentsNumber(request.getDependentsNumber());
        debtor.setHousingStatus(request.getHousingStatus());
        debtor.setFinancialDifficulties(request.getFinancialDifficulties());
        debtor.setProfileVerified(request.getProfileVerified() != null ? request.getProfileVerified() : false);
        debtor.setGender(request.getGender());
        debtor.setPreferredLanguage(request.getPreferredLanguage());

        Debtor savedDebtor = debtorRepository.save(debtor);
        logger.info("Successfully created debtor with ID: {}", savedDebtor.getUserId());

        return savedDebtor;
    }

    @Override
    public Debtor update(Long id, DebtorRequest request) {
        logger.info("Updating debtor with ID: {}", id);

        Debtor debtor = findById(id);

        // Update User fields
        debtor.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            debtor.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        debtor.setEmail(request.getEmail());
        debtor.setFirstName(request.getFirstName());
        debtor.setLastName(request.getLastName());
        debtor.setProfilePictureUrl(request.getProfilePictureUrl());

        // Update Debtor-specific fields
        debtor.setPhoneNumber(request.getPhoneNumber());
        debtor.setAddress(request.getAddress());
        debtor.setDateOfBirth(request.getDateOfBirth());
        debtor.setIncomeLevel(request.getIncomeLevel());
        debtor.setEmploymentStatus(request.getEmploymentStatus());
        debtor.setDebtReason(request.getDebtReason());
        debtor.setMaritalStatus(request.getMaritalStatus());
        debtor.setDependentsNumber(request.getDependentsNumber());
        debtor.setHousingStatus(request.getHousingStatus());
        debtor.setFinancialDifficulties(request.getFinancialDifficulties());
        debtor.setProfileVerified(request.getProfileVerified());
        debtor.setGender(request.getGender());
        debtor.setPreferredLanguage(request.getPreferredLanguage());

        Debtor updatedDebtor = debtorRepository.save(debtor);
        logger.info("Successfully updated debtor with ID: {}", id);

        return updatedDebtor;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting debtor with ID: {}", id);
        Debtor debtor = findById(id);
        debtorRepository.delete(debtor);
        logger.info("Successfully deleted debtor with ID: {}", id);
    }

    @Override
    public DebtorResponse toResponse(Debtor debtor) {
        DebtorResponse response = new DebtorResponse();

        // Map User fields
        response.setUserId(debtor.getUserId());
        response.setUsername(debtor.getUsername());
        response.setEmail(debtor.getEmail());
        response.setFirstName(debtor.getFirstName());
        response.setLastName(debtor.getLastName());
        response.setPhoneNumber(debtor.getPhoneNumber());
        response.setProfilePictureUrl(debtor.getProfilePictureUrl());
        response.setStatus(debtor.getStatus());
        response.setCreatedAt(debtor.getCreatedAt());
        response.setUpdatedAt(debtor.getUpdatedAt());
        response.setLastLogin(debtor.getLastLogin());

        // Map AppUser fields
        response.setRegistrationDate(debtor.getRegistrationDate());

        // Map Debtor-specific fields
        response.setAddress(debtor.getAddress());
        response.setDateOfBirth(debtor.getDateOfBirth());
        response.setIncomeLevel(debtor.getIncomeLevel());
        response.setEmploymentStatus(debtor.getEmploymentStatus());
        response.setDebtReason(debtor.getDebtReason());
        response.setMaritalStatus(debtor.getMaritalStatus());
        response.setDependentsNumber(debtor.getDependentsNumber());
        response.setHousingStatus(debtor.getHousingStatus());
        response.setFinancialDifficulties(debtor.getFinancialDifficulties());
        response.setProfileVerified(debtor.getProfileVerified());
        response.setGender(debtor.getGender());
        response.setPreferredLanguage(debtor.getPreferredLanguage());

        return response;
    }
}
