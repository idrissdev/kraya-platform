package com.kraya.platform.service;

import com.kraya.platform.dto.VoteRequest;
import com.kraya.platform.model.Vote;

import java.util.List;

/**
 * VoteService interface defines the contract for vote-related operations.
 */
public interface VoteService {

    /**
     * Retrieves all votes.
     *
     * @return a list of votes
     */
    List<Vote> findAll();

    /**
     * Retrieves a vote by ID.
     *
     * @param id the ID of the vote to retrieve
     * @return the vote object if found
     */
    Vote findById(Long id);

    /**
     * Creates a new vote based on the request.
     *
     * @param request the vote request containing necessary data
     * @return the created vote
     */
    Vote create(VoteRequest request);

    /**
     * Updates an existing vote's information.
     *
     * @param id      the ID of the vote to update
     * @param request the updated vote data
     * @return the updated vote
     */
    Vote update(Long id, VoteRequest request);

    /**
     * Deletes a vote by ID.
     *
     * @param id the ID of the vote to delete
     */
    void delete(Long id);

    /**
     * Finds all votes for a specific debtor.
     *
     * @param debtorId the debtor ID
     * @return list of votes
     */
    List<Vote> findByDebtorId(Long debtorId);

    /**
     * Finds all votes from a specific creditor.
     *
     * @param creditorId the creditor ID
     * @return list of votes
     */
    List<Vote> findByCreditorId(Long creditorId);

    /**
     * Finds all votes for a specific debtor with a specific vote value.
     *
     * @param debtorId the debtor ID
     * @param vote     the vote value (true for yes, false for no)
     * @return list of votes
     */
    List<Vote> findByDebtorIdAndVote(Long debtorId, Boolean vote);
}
