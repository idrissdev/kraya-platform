package com.kraya.platform.dto;

import lombok.Data;

/**
 * RoleResponse contains the data returned after role operations.
 */
@Data
public class RoleResponse {

    private Long id;
    private String name;

    // Additional fields can be added here if needed
}
