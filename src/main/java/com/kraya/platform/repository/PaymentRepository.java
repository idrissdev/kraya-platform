package com.kraya.platform.repository;

import com.kraya.platform.model.Debt;
import com.kraya.platform.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PaymentRepository provides methods for accessing payment data.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Finds all payments for a specific debt.
     *
     * @param debt the debt
     * @return list of payments
     */
    List<Payment> findByDebt(Debt debt);

    /**
     * Finds all payments for a specific debt, ordered by transaction date descending.
     *
     * @param debt the debt
     * @return list of payments
     */
    List<Payment> findByDebtOrderByTransactionDateDesc(Debt debt);
}
