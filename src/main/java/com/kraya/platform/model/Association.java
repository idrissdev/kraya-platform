
package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an association in the Kraya platform.
 */
@Entity
@Table(name = "association")
@Data
@NoArgsConstructor
public class Association extends AppUser {

    private String areaOfFocus;
    private String taxId;
    private String registrationNumber;
}