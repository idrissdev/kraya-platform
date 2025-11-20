package com.kraya.platform.controller;

import com.kraya.platform.dto.VoteRequest;
import com.kraya.platform.dto.VoteResponse;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.model.Vote;
import com.kraya.platform.service.VoteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * VoteController handles vote-related HTTP requests, providing endpoints
 * for vote creation, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    /**
     * Creates a new vote.
     *
     * @param request the vote creation request
     * @return ResponseEntity with vote response and created status
     */
    @PostMapping
    public ResponseEntity<VoteResponse> createVote(@Valid @RequestBody VoteRequest request) {
        logger.info("Received request to create vote for debtor ID: {}", request.getDebtorId());

        try {
            Vote vote = voteService.create(request);
            VoteResponse response = new VoteResponse(vote.getVoteId(), "Vote created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResourceNotFoundException e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new VoteResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Creation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new VoteResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all votes.
     *
     * @return ResponseEntity containing the list of votes
     */
    @GetMapping
    public ResponseEntity<List<Vote>> getAllVotes() {
        logger.info("Fetching all votes");
        List<Vote> votes = voteService.findAll();
        return ResponseEntity.ok(votes);
    }

    /**
     * Retrieves a vote by ID.
     *
     * @param voteId the ID of the vote to retrieve
     * @return ResponseEntity containing the vote data if found
     */
    @GetMapping("/{voteId}")
    public ResponseEntity<Vote> getVoteById(@PathVariable Long voteId) {
        logger.info("Fetching vote with ID: {}", voteId);
        try {
            Vote vote = voteService.findById(voteId);
            return ResponseEntity.ok(vote);
        } catch (ResourceNotFoundException e) {
            logger.error("Vote not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all votes for a specific debtor.
     *
     * @param debtorId the debtor ID
     * @return ResponseEntity containing the list of votes
     */
    @GetMapping("/debtor/{debtorId}")
    public ResponseEntity<List<Vote>> getVotesByDebtorId(@PathVariable Long debtorId) {
        logger.info("Fetching votes for debtor ID: {}", debtorId);
        try {
            List<Vote> votes = voteService.findByDebtorId(debtorId);
            return ResponseEntity.ok(votes);
        } catch (ResourceNotFoundException e) {
            logger.error("Debtor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all votes from a specific creditor.
     *
     * @param creditorId the creditor ID
     * @return ResponseEntity containing the list of votes
     */
    @GetMapping("/creditor/{creditorId}")
    public ResponseEntity<List<Vote>> getVotesByCreditorId(@PathVariable Long creditorId) {
        logger.info("Fetching votes from creditor ID: {}", creditorId);
        try {
            List<Vote> votes = voteService.findByCreditorId(creditorId);
            return ResponseEntity.ok(votes);
        } catch (ResourceNotFoundException e) {
            logger.error("Creditor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all votes for a specific debtor with a specific vote value.
     *
     * @param debtorId the debtor ID
     * @param voteValue the vote value (true for yes, false for no)
     * @return ResponseEntity containing the list of votes
     */
    @GetMapping("/debtor/{debtorId}/vote/{voteValue}")
    public ResponseEntity<List<Vote>> getVotesByDebtorIdAndVote(@PathVariable Long debtorId, @PathVariable Boolean voteValue) {
        logger.info("Fetching votes for debtor ID: {} with vote value: {}", debtorId, voteValue);
        try {
            List<Vote> votes = voteService.findByDebtorIdAndVote(debtorId, voteValue);
            return ResponseEntity.ok(votes);
        } catch (ResourceNotFoundException e) {
            logger.error("Debtor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates vote information.
     *
     * @param voteId  the ID of the vote to update
     * @param request the vote update request
     * @return ResponseEntity with a success message
     */
    @PutMapping("/{voteId}")
    public ResponseEntity<VoteResponse> updateVote(@PathVariable Long voteId, @Valid @RequestBody VoteRequest request) {
        logger.info("Updating vote with ID: {}", voteId);

        try {
            voteService.update(voteId, request);
            return ResponseEntity.ok(new VoteResponse(voteId, "Vote updated successfully"));
        } catch (ResourceNotFoundException e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new VoteResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new VoteResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a vote by ID.
     *
     * @param voteId the ID of the vote to delete
     * @return ResponseEntity with No Content status if deletion is successful
     */
    @DeleteMapping("/{voteId}")
    public ResponseEntity<VoteResponse> deleteVote(@PathVariable Long voteId) {
        logger.info("Deleting vote with ID: {}", voteId);

        try {
            voteService.delete(voteId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new VoteResponse(e.getMessage()));
        }
    }

    /**
     * Handles ResourceNotFoundException and returns a meaningful response.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with error message and HTTP status NOT_FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
