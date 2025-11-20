package com.kraya.platform.service.impl;

import com.kraya.platform.dto.RecommendationRequest;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Recommendation;
import com.kraya.platform.repository.RecommendationRepository;
import com.kraya.platform.repository.UserRepository;
import com.kraya.platform.service.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * RecommendationServiceImpl provides the implementation for recommendation-related operations.
 */
@Service
@Transactional
public class RecommendationServiceImpl implements RecommendationService {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecommendationServiceImpl(RecommendationRepository recommendationRepository,
                                     UserRepository userRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Recommendation> findAll() {
        logger.info("Fetching all recommendations");
        return recommendationRepository.findAll();
    }

    @Override
    public Recommendation findById(Long id) {
        logger.info("Fetching recommendation with ID: {}", id);
        return recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with ID: " + id));
    }

    @Override
    public Recommendation create(RecommendationRequest request) {
        logger.info("Creating new recommendation for debtor ID: {} from creditor ID: {}",
                request.getDebtorId(), request.getCreditorId());

        // Validate debtor exists
        AppUser debtor = (AppUser) userRepository.findById(request.getDebtorId())
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + request.getDebtorId()));

        // Validate creditor exists
        AppUser creditor = (AppUser) userRepository.findById(request.getCreditorId())
                .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + request.getCreditorId()));

        Recommendation recommendation = new Recommendation();
        recommendation.setDebtor(debtor);
        recommendation.setCreditor(creditor);
        recommendation.setComment(request.getComment());
        recommendation.setRecommendationDate(LocalDateTime.now());

        return recommendationRepository.save(recommendation);
    }

    @Override
    public Recommendation update(Long id, RecommendationRequest request) {
        logger.info("Updating recommendation with ID: {}", id);

        Recommendation recommendation = findById(id);

        // Update debtor if changed
        if (!recommendation.getDebtor().getUserId().equals(request.getDebtorId())) {
            AppUser debtor = (AppUser) userRepository.findById(request.getDebtorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + request.getDebtorId()));
            recommendation.setDebtor(debtor);
        }

        // Update creditor if changed
        if (!recommendation.getCreditor().getUserId().equals(request.getCreditorId())) {
            AppUser creditor = (AppUser) userRepository.findById(request.getCreditorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + request.getCreditorId()));
            recommendation.setCreditor(creditor);
        }

        recommendation.setComment(request.getComment());

        return recommendationRepository.save(recommendation);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting recommendation with ID: {}", id);
        Recommendation recommendation = findById(id);
        recommendationRepository.delete(recommendation);
    }

    @Override
    public List<Recommendation> findByDebtorId(Long debtorId) {
        logger.info("Fetching recommendations for debtor ID: {}", debtorId);
        AppUser debtor = (AppUser) userRepository.findById(debtorId)
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + debtorId));
        return recommendationRepository.findByDebtor(debtor);
    }

    @Override
    public List<Recommendation> findByCreditorId(Long creditorId) {
        logger.info("Fetching recommendations from creditor ID: {}", creditorId);
        AppUser creditor = (AppUser) userRepository.findById(creditorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + creditorId));
        return recommendationRepository.findByCreditor(creditor);
    }
}
