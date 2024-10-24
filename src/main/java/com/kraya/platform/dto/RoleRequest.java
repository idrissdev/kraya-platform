package com.kraya.platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * RoleRequest contains the data required for creating or updating a role.
 */
@Data
public class RoleRequest {

    @NotBlank(message = "Role name is mandatory")
    private String name;

    // Additional fields can be added here if needed
}
