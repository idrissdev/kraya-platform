package com.kraya.platform.repository;

import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * VoteRepository provides methods for accessing vote data.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * Finds all votes for a specific debtor.
     *
     * @param debtor the debtor
     * @return list of votes
     */
    List<Vote> findByDebtor(AppUser debtor);

    /**
     * Finds all votes from a specific creditor.
     *
     * @param creditor the creditor who voted
     * @return list of votes
     */
    List<Vote> findByCreditor(AppUser creditor);

    /**
     * Finds all votes for a specific debtor with a specific vote value.
     *
     * @param debtor the debtor
     * @param vote   the vote value (true for yes, false for no)
     * @return list of votes
     */
    List<Vote> findByDebtorAndVote(AppUser debtor, Boolean vote);
}
