package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Represents a debtor in the Kraya platform.
 */
@Entity
@Table(name = "debtor")
@Data
@NoArgsConstructor
public class Debtor extends AppUser {

    private String phoneNumber;
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
}