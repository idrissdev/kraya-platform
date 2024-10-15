
package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Represents a vote in the Kraya platform.
 */
@Entity
@Table(name = "vote")
@Data
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne
    @JoinColumn(name = "debtor_id", nullable = false)
    private AppUser debtor;

    @ManyToOne
    @JoinColumn(name = "creditor_id", nullable = false)
    private AppUser creditor;

    @Column(nullable = false)
    private Boolean vote;

    @Column(nullable = false)
    private LocalDateTime voteDate;

    private String comment;
}