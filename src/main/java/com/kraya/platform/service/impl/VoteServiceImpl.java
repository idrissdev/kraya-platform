package com.kraya.platform.service.impl;

import com.kraya.platform.dto.VoteRequest;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Vote;
import com.kraya.platform.repository.UserRepository;
import com.kraya.platform.repository.VoteRepository;
import com.kraya.platform.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * VoteServiceImpl provides the implementation for vote-related operations.
 */
@Service
@Transactional
public class VoteServiceImpl implements VoteService {

    private static final Logger logger = LoggerFactory.getLogger(VoteServiceImpl.class);

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository,
                          UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Vote> findAll() {
        logger.info("Fetching all votes");
        return voteRepository.findAll();
    }

    @Override
    public Vote findById(Long id) {
        logger.info("Fetching vote with ID: {}", id);
        return voteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote not found with ID: " + id));
    }

    @Override
    public Vote create(VoteRequest request) {
        logger.info("Creating new vote for debtor ID: {} from creditor ID: {}",
                request.getDebtorId(), request.getCreditorId());

        // Validate debtor exists
        AppUser debtor = (AppUser) userRepository.findById(request.getDebtorId())
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + request.getDebtorId()));

        // Validate creditor exists
        AppUser creditor = (AppUser) userRepository.findById(request.getCreditorId())
                .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + request.getCreditorId()));

        Vote vote = new Vote();
        vote.setDebtor(debtor);
        vote.setCreditor(creditor);
        vote.setVote(request.getVote());
        vote.setVoteDate(LocalDateTime.now());
        vote.setComment(request.getComment());

        return voteRepository.save(vote);
    }

    @Override
    public Vote update(Long id, VoteRequest request) {
        logger.info("Updating vote with ID: {}", id);

        Vote vote = findById(id);

        // Update debtor if changed
        if (!vote.getDebtor().getUserId().equals(request.getDebtorId())) {
            AppUser debtor = (AppUser) userRepository.findById(request.getDebtorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + request.getDebtorId()));
            vote.setDebtor(debtor);
        }

        // Update creditor if changed
        if (!vote.getCreditor().getUserId().equals(request.getCreditorId())) {
            AppUser creditor = (AppUser) userRepository.findById(request.getCreditorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + request.getCreditorId()));
            vote.setCreditor(creditor);
        }

        vote.setVote(request.getVote());
        vote.setComment(request.getComment());

        return voteRepository.save(vote);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting vote with ID: {}", id);
        Vote vote = findById(id);
        voteRepository.delete(vote);
    }

    @Override
    public List<Vote> findByDebtorId(Long debtorId) {
        logger.info("Fetching votes for debtor ID: {}", debtorId);
        AppUser debtor = (AppUser) userRepository.findById(debtorId)
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + debtorId));
        return voteRepository.findByDebtor(debtor);
    }

    @Override
    public List<Vote> findByCreditorId(Long creditorId) {
        logger.info("Fetching votes from creditor ID: {}", creditorId);
        AppUser creditor = (AppUser) userRepository.findById(creditorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creditor not found with ID: " + creditorId));
        return voteRepository.findByCreditor(creditor);
    }

    @Override
    public List<Vote> findByDebtorIdAndVote(Long debtorId, Boolean vote) {
        logger.info("Fetching votes for debtor ID: {} with vote value: {}", debtorId, vote);
        AppUser debtor = (AppUser) userRepository.findById(debtorId)
                .orElseThrow(() -> new ResourceNotFoundException("Debtor not found with ID: " + debtorId));
        return voteRepository.findByDebtorAndVote(debtor, vote);
    }
}
