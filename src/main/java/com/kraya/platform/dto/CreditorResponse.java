package com.kraya.platform.dto;

import com.kraya.platform.model.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * CreditorResponse contains the data returned after creditor operations.
 */
@Data
public class CreditorResponse {

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

    // Creditor-specific fields
    private String contactPerson;
    private String address;
    private String website;
    private Boolean verified;
    private String creditRating;
    private Integer yearsInBusiness;
    private String businessLicense;
    private String apiKey;

    private String message;

    public CreditorResponse() {}

    public CreditorResponse(String message) {
        this.message = message;
    }

    public CreditorResponse(Long userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}
