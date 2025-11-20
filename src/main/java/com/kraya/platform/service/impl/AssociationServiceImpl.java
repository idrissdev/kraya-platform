package com.kraya.platform.service.impl;

import com.kraya.platform.dto.AssociationRequest;
import com.kraya.platform.dto.AssociationResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Association;
import com.kraya.platform.model.User;
import com.kraya.platform.repository.AssociationRepository;
import com.kraya.platform.service.AssociationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AssociationServiceImpl provides the implementation for association-related operations.
 */
@Service
@Transactional
public class AssociationServiceImpl implements AssociationService {

    private static final Logger logger = LoggerFactory.getLogger(AssociationServiceImpl.class);

    private final AssociationRepository associationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AssociationServiceImpl(AssociationRepository associationRepository, PasswordEncoder passwordEncoder) {
        this.associationRepository = associationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Association> findAll() {
        logger.info("Retrieving all associations");
        return associationRepository.findAll();
    }

    @Override
    public Association findById(Long id) {
        logger.info("Retrieving association with ID: {}", id);
        return associationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Association not found with ID: " + id));
    }

    @Override
    public Association create(AssociationRequest request) {
        logger.info("Creating new association with username: {}", request.getUsername());

        // Validate if the association already exists
        associationRepository.findByEmail(request.getEmail()).ifPresent(association -> {
            throw new IllegalArgumentException("Email already exists.");
        });

        if (request.getRegistrationNumber() != null) {
            associationRepository.findByRegistrationNumber(request.getRegistrationNumber()).ifPresent(association -> {
                throw new IllegalArgumentException("Registration number already exists.");
            });
        }

        Association association = new Association();

        // Set User fields
        association.setUsername(request.getUsername());
        association.setPassword(passwordEncoder.encode(request.getPassword()));
        association.setEmail(request.getEmail());
        association.setFirstName(request.getFirstName());
        association.setLastName(request.getLastName());
        association.setPhoneNumber(request.getPhoneNumber());
        association.setProfilePictureUrl(request.getProfilePictureUrl());
        association.setStatus(User.Status.ACTIVE);

        // Set AppUser fields
        association.setRegistrationDate(LocalDateTime.now());

        // Set Association-specific fields
        association.setAreaOfFocus(request.getAreaOfFocus());
        association.setTaxId(request.getTaxId());
        association.setRegistrationNumber(request.getRegistrationNumber());

        Association savedAssociation = associationRepository.save(association);
        logger.info("Successfully created association with ID: {}", savedAssociation.getUserId());

        return savedAssociation;
    }

    @Override
    public Association update(Long id, AssociationRequest request) {
        logger.info("Updating association with ID: {}", id);

        Association association = findById(id);

        // Update User fields
        association.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            association.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        association.setEmail(request.getEmail());
        association.setFirstName(request.getFirstName());
        association.setLastName(request.getLastName());
        association.setPhoneNumber(request.getPhoneNumber());
        association.setProfilePictureUrl(request.getProfilePictureUrl());

        // Update Association-specific fields
        association.setAreaOfFocus(request.getAreaOfFocus());
        association.setTaxId(request.getTaxId());
        association.setRegistrationNumber(request.getRegistrationNumber());

        Association updatedAssociation = associationRepository.save(association);
        logger.info("Successfully updated association with ID: {}", id);

        return updatedAssociation;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting association with ID: {}", id);
        Association association = findById(id);
        associationRepository.delete(association);
        logger.info("Successfully deleted association with ID: {}", id);
    }

    @Override
    public AssociationResponse toResponse(Association association) {
        AssociationResponse response = new AssociationResponse();

        // Map User fields
        response.setUserId(association.getUserId());
        response.setUsername(association.getUsername());
        response.setEmail(association.getEmail());
        response.setFirstName(association.getFirstName());
        response.setLastName(association.getLastName());
        response.setPhoneNumber(association.getPhoneNumber());
        response.setProfilePictureUrl(association.getProfilePictureUrl());
        response.setStatus(association.getStatus());
        response.setCreatedAt(association.getCreatedAt());
        response.setUpdatedAt(association.getUpdatedAt());
        response.setLastLogin(association.getLastLogin());

        // Map AppUser fields
        response.setRegistrationDate(association.getRegistrationDate());

        // Map Association-specific fields
        response.setAreaOfFocus(association.getAreaOfFocus());
        response.setTaxId(association.getTaxId());
        response.setRegistrationNumber(association.getRegistrationNumber());

        return response;
    }
}
