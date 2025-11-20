package com.kraya.platform.repository;

import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RecommendationRepository provides methods for accessing recommendation data.
 */
@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    /**
     * Finds all recommendations for a specific debtor.
     *
     * @param debtor the debtor
     * @return list of recommendations
     */
    List<Recommendation> findByDebtor(AppUser debtor);

    /**
     * Finds all recommendations from a specific creditor.
     *
     * @param creditor the creditor who made the recommendation
     * @return list of recommendations
     */
    List<Recommendation> findByCreditor(AppUser creditor);
}
