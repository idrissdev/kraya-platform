package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Role represents a user role in the system.
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // e.g., "USER", "ADMIN", etc.

    // Constructor that takes a role name
    public Role(String name) {
        this.name = name;
    }

    // Additional fields can be added here if needed
}
