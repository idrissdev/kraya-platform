package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Represents a document in the Kraya platform.
 */
@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @ManyToOne
    @JoinColumn(name = "debtor_id", nullable = false)
    private AppUser debtor;

    @Column(nullable = false)
    private String documentType;

    @Column(nullable = false)
    private String documentPath;

    @Column(nullable = false)
    private LocalDateTime uploadDate;

    @Column(nullable = false)
    private String verificationStatus;
}
