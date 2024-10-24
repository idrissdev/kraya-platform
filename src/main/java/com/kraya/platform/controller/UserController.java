package com.kraya.platform.controller;

import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.dto.UserRegistrationResponse;
import com.kraya.platform.dto.UserUpdateRequest;
import com.kraya.platform.exception.InvalidRoleException;
import com.kraya.platform.exception.UserNotFoundException;
import com.kraya.platform.model.User;
import com.kraya.platform.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * UserController handles user-related HTTP requests, providing endpoints
 * for user registration, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user.
     *
     * @param request the user registration request containing necessary data
     * @return ResponseEntity with user registration response and created status
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        logger.info("Received registration request for username: {}", request.getUsername());

        try {
            ResponseEntity<UserRegistrationResponse> response = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
        } catch (IllegalArgumentException e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserRegistrationResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId the ID of the user to retrieve
     * @return ResponseEntity containing the user data if found, otherwise Not Found status
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        logger.info("Fetching user with ID: {}", userId);
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Updates user information.
     *
     * @param userId  the ID of the user to update
     * @param request the user update request containing updated data
     * @return ResponseEntity with a success message and OK status if update is successful, otherwise Not Found or Bad Request status
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserRegistrationResponse> updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequest request) {
        logger.info("Updating user with ID: {}", userId);

        try {
            User updatedUser = userService.update(userId, request);
            return ResponseEntity.ok(new UserRegistrationResponse(updatedUser.getUserId(), "User updated successfully"));
        } catch (UserNotFoundException e) {
            logger.error("Update failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserRegistrationResponse(e.getMessage()));
        } catch (InvalidRoleException e) {
            logger.error("Invalid role error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserRegistrationResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId the ID of the user to delete
     * @return ResponseEntity with No Content status if deletion is successful, otherwise Not Found status
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        logger.info("Deleting user with ID: {}", userId);

        try {
            userService.delete(userId);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Handles UserNotFoundException and returns a meaningful response.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with error message and HTTP status NOT_FOUND
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        logger.error("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles InvalidRoleException and returns a meaningful response.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<String> handleInvalidRole(InvalidRoleException ex) {
        logger.warn("Invalid role error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles MethodArgumentNotValidException and returns validation errors.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity with validation error messages and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}