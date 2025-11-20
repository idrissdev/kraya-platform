package com.kraya.platform.dto;

import com.kraya.platform.model.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DebtorResponse contains the data returned after debtor operations.
 */
@Data
public class DebtorResponse {

    // User fields
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureUrl;
    private User.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

    // AppUser fields
    private LocalDateTime registrationDate;

    // Debtor-specific fields
    private String address;
    private LocalDate dateOfBirth;
    private String incomeLevel;
    private String employmentStatus;
    private String debtReason;
    private String maritalStatus;
    private Integer dependentsNumber;
    private String housingStatus;
    private String financialDifficulties;
    private Boolean profileVerified;
    private String gender;
    private String preferredLanguage;

    private String message;

    public DebtorResponse() {}

    public DebtorResponse(String message) {
        this.message = message;
    }

    public DebtorResponse(Long userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}
