package com.kraya.platform.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponseDto {

    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;  // List of role names
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    // Additional fields like phone number, profile picture, etc., can be added here if needed
}
