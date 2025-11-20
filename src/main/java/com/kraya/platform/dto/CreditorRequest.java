package com.kraya.platform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * CreditorRequest contains the data required for creditor registration and updates.
 */
@Data
public class CreditorRequest {

    // User fields
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    private String profilePictureUrl;

    // Creditor-specific fields
    private String contactPerson;
    private String phoneNumber;
    private String address;
    private String website;
    private Boolean verified;
    private String creditRating;
    private Integer yearsInBusiness;
    private String businessLicense;
    private String apiKey;
}
