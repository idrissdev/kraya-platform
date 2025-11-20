package com.kraya.platform.repository;

import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.Debt;
import com.kraya.platform.model.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PaymentPlanRepository provides methods for accessing payment plan data.
 */
@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {

    /**
     * Finds all payment plans for a specific debt.
     *
     * @param debt the debt
     * @return list of payment plans
     */
    List<PaymentPlan> findByDebt(Debt debt);

    /**
     * Finds all payment plans created by a specific creditor.
     *
     * @param creditor the creditor
     * @return list of payment plans
     */
    List<PaymentPlan> findByCreditor(AppUser creditor);
}
