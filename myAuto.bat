@echo off
set DTO_PATH=C:\Users\Production\IdeaProjects\kraya-platform\src\main\java\com\kraya\platform\dto

echo Filling UserRegistrationRequest.java...

(
    echo package com.kraya.platform.dto;
    echo.
    echo import jakarta.validation.constraints.Email;
    echo import jakarta.validation.constraints.NotBlank;
    echo import jakarta.validation.constraints.Size;
    echo import lombok.Data;
    echo.
    echo @Data;
    echo public class UserRegistrationRequest ^{
    echo.
    echo     @NotBlank(message = "Username is mandatory")
    echo     @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    echo     private String username;
    echo.
    echo     @NotBlank(message = "Password is mandatory")
    echo     @Size(min = 6, message = "Password must be at least 6 characters long")
    echo     private String password;
    echo.
    echo     @NotBlank(message = "First name is mandatory")
    echo     private String firstName;
    echo.
    echo     @NotBlank(message = "Last name is mandatory")
    echo     private String lastName;
    echo.
    echo     @NotBlank(message = "Role is mandatory")
    echo     private String role;
    echo.
    echo     @NotBlank(message = "Email is mandatory")
    echo     @Email(message = "Email should be valid")
    echo     private String email;
    echo.
    echo     // Optionally add more fields like phone number or profile picture URL here if needed
    echo ^}
) > "%DTO_PATH%\UserRegistrationRequest.java"

echo UserRegistrationRequest.java has been filled.
