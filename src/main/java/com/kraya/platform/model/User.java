package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    // New Fields
    @Column(nullable = true, unique = true)
    private String phoneNumber;  // Optional, unique

    @Column(nullable = true)
    private String profilePictureUrl;  // Optional

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;  // New Enum to manage statuses like ACTIVE, INACTIVE, etc.

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;  // Automatically managed by Hibernate

    @UpdateTimestamp
    private LocalDateTime updatedAt;  // Automatically managed by Hibernate

    @Column(nullable = true)
    private LocalDateTime lastLogin;  // To store the last login time

    // Many-to-Many relationship with Role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    // Enum for Status
    public enum Status {
        ACTIVE,
        INACTIVE,
        BANNED
    }
}
