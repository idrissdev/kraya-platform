package com.kraya.platform.dto;

import lombok.Data;

@Data
public class UserRegistrationResponse {

    private Long userId;  // The ID of the newly registered user
    private String message;  // Success or error message

    public UserRegistrationResponse() {}

    public UserRegistrationResponse(String message) {
        this.message = message;
    }

    // Additional fields can be added as needed
}
