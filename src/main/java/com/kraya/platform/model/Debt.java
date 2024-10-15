package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a debt in the Kraya platform.
 */
@Entity
@Table(name = "debt")
@Data
@NoArgsConstructor
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long debtId;

    @ManyToOne
    @JoinColumn(name = "debtor_id", nullable = false)
    private AppUser debtor;

    @ManyToOne
    @JoinColumn(name = "creditor_id", nullable = false)
    private AppUser creditor;

    @Column(nullable = false)
    private BigDecimal originalAmount;

    @Column(nullable = false)
    private BigDecimal currentAmount;

    private BigDecimal interestRate;
    private LocalDate dueDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime creationDate;
}
