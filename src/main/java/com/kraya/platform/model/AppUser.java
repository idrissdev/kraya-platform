package com.kraya.platform.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AppUser extends User {

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    // Additional application-specific fields can be added here as needed
}
