package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a debt transfer in the Kraya platform.
 */
@Entity
@Table(name = "debt_transfer")
@Data
@NoArgsConstructor
public class DebtTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferId;

    @ManyToOne
    @JoinColumn(name = "debt_id", nullable = false)
    private Debt debt;

    @ManyToOne
    @JoinColumn(name = "from_creditor_id", nullable = false)
    private AppUser fromCreditor;

    @ManyToOne
    @JoinColumn(name = "to_creditor_id", nullable = false)
    private AppUser toCreditor;

    @Column(nullable = false)
    private LocalDateTime transferDate;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status;
}
