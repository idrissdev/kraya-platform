package com.kraya.platform.service.impl;

import com.kraya.platform.dto.CreditorRequest;
import com.kraya.platform.dto.CreditorResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Creditor;
import com.kraya.platform.model.User;
import com.kraya.platform.repository.CreditorRepository;
import com.kraya.platform.service.CreditorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CreditorServiceImpl provides the implementation for creditor-related operations.
 */
@Service
@Transactional
public class CreditorServiceImpl implements CreditorService {

    private static final Logger logger = LoggerFactory.getLogger(CreditorServiceImpl.class);

    private final CreditorRepository creditorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CreditorServiceImpl(CreditorRepository creditorRepository, PasswordEncoder passwordEncoder) {
        this.creditorRepository = creditorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Creditor> findAll() {
        logger.info("Retrieving all creditors");
        return creditorRepository.findAll();
    }

    @Override
    public Creditor findById(Long id) {
        logger.info("Retrieving creditor with ID: {}", id);
        return creditorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + id));
    }

    @Override
    public Creditor create(CreditorRequest request) {
        logger.info("Creating new creditor with username: {}", request.getUsername());

        // Validate if the creditor already exists
        creditorRepository.findByEmail(request.getEmail()).ifPresent(creditor -> {
            throw new IllegalArgumentException("Email already exists.");
        });

        Creditor creditor = new Creditor();

        // Set User fields
        creditor.setUsername(request.getUsername());
        creditor.setPassword(passwordEncoder.encode(request.getPassword()));
        creditor.setEmail(request.getEmail());
        creditor.setFirstName(request.getFirstName());
        creditor.setLastName(request.getLastName());
        creditor.setProfilePictureUrl(request.getProfilePictureUrl());
        creditor.setStatus(User.Status.ACTIVE);

        // Set AppUser fields
        creditor.setRegistrationDate(LocalDateTime.now());

        // Set Creditor-specific fields
        creditor.setContactPerson(request.getContactPerson());
        creditor.setPhoneNumber(request.getPhoneNumber());
        creditor.setAddress(request.getAddress());
        creditor.setWebsite(request.getWebsite());
        creditor.setVerified(request.getVerified() != null ? request.getVerified() : false);
        creditor.setCreditRating(request.getCreditRating());
        creditor.setYearsInBusiness(request.getYearsInBusiness());
        creditor.setBusinessLicense(request.getBusinessLicense());
        creditor.setApiKey(request.getApiKey());

        Creditor savedCreditor = creditorRepository.save(creditor);
        logger.info("Successfully created creditor with ID: {}", savedCreditor.getUserId());

        return savedCreditor;
    }

    @Override
    public Creditor update(Long id, CreditorRequest request) {
        logger.info("Updating creditor with ID: {}", id);

        Creditor creditor = findById(id);

        // Update User fields
        creditor.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            creditor.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        creditor.setEmail(request.getEmail());
        creditor.setFirstName(request.getFirstName());
        creditor.setLastName(request.getLastName());
        creditor.setProfilePictureUrl(request.getProfilePictureUrl());

        // Update Creditor-specific fields
        creditor.setContactPerson(request.getContactPerson());
        creditor.setPhoneNumber(request.getPhoneNumber());
        creditor.setAddress(request.getAddress());
        creditor.setWebsite(request.getWebsite());
        creditor.setVerified(request.getVerified());
        creditor.setCreditRating(request.getCreditRating());
        creditor.setYearsInBusiness(request.getYearsInBusiness());
        creditor.setBusinessLicense(request.getBusinessLicense());
        creditor.setApiKey(request.getApiKey());

        Creditor updatedCreditor = creditorRepository.save(creditor);
        logger.info("Successfully updated creditor with ID: {}", id);

        return updatedCreditor;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting creditor with ID: {}", id);
        Creditor creditor = findById(id);
        creditorRepository.delete(creditor);
        logger.info("Successfully deleted creditor with ID: {}", id);
    }

    @Override
    public CreditorResponse toResponse(Creditor creditor) {
        CreditorResponse response = new CreditorResponse();

        // Map User fields
        response.setUserId(creditor.getUserId());
        response.setUsername(creditor.getUsername());
        response.setEmail(creditor.getEmail());
        response.setFirstName(creditor.getFirstName());
        response.setLastName(creditor.getLastName());
        response.setPhoneNumber(creditor.getPhoneNumber());
        response.setProfilePictureUrl(creditor.getProfilePictureUrl());
        response.setStatus(creditor.getStatus());
        response.setCreatedAt(creditor.getCreatedAt());
        response.setUpdatedAt(creditor.getUpdatedAt());
        response.setLastLogin(creditor.getLastLogin());

        // Map AppUser fields
        response.setRegistrationDate(creditor.getRegistrationDate());

        // Map Creditor-specific fields
        response.setContactPerson(creditor.getContactPerson());
        response.setAddress(creditor.getAddress());
        response.setWebsite(creditor.getWebsite());
        response.setVerified(creditor.getVerified());
        response.setCreditRating(creditor.getCreditRating());
        response.setYearsInBusiness(creditor.getYearsInBusiness());
        response.setBusinessLicense(creditor.getBusinessLicense());
        response.setApiKey(creditor.getApiKey());

        return response;
    }
}
