package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a creditor in the Kraya platform.
 */
@Entity
@Table(name = "creditor")
@Data
@NoArgsConstructor
public class Creditor extends AppUser {

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