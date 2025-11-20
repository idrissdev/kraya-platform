package com.kraya.platform.dto;

import com.kraya.platform.model.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * AssociationResponse contains the data returned after association operations.
 */
@Data
public class AssociationResponse {

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

    // Association-specific fields
    private String areaOfFocus;
    private String taxId;
    private String registrationNumber;

    private String message;

    public AssociationResponse() {}

    public AssociationResponse(String message) {
        this.message = message;
    }

    public AssociationResponse(Long userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}
