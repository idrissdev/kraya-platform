package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Represents a recommendation in the Kraya platform.
 */
@Entity
@Table(name = "recommendation")
@Data
@NoArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;

    @ManyToOne
    @JoinColumn(name = "debtor_id", nullable = false)
    private AppUser debtor;

    @ManyToOne
    @JoinColumn(name = "creditor_id", nullable = false)
    private AppUser creditor;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime recommendationDate;
}
