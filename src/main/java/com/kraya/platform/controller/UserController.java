package com.kraya.platform.controller;

import com.kraya.platform.dto.UserDeletionResponse;
import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.dto.UserRegistrationResponse;
import com.kraya.platform.dto.UserUpdateRequest;
import com.kraya.platform.exception.InvalidRoleException;
import com.kraya.platform.exception.UserNotFoundException;
import com.kraya.platform.model.User;
import com.kraya.platform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * UserController handles user-related HTTP requests, providing endpoints
 * for user registration, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/users")  // Base URL for user-related operations
public class UserController { //extends GenericController<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class); // Logger for this class

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

   // @Override
    protected UserService getService() {
        return userService;
    }

    /**
     * Registers a new user.
     *
     * @param request the user registration request containing necessary data
     * @return ResponseEntity with user registration response
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        logger.info("Received registration request for username: {}", request.getUsername());

        try {
            return userService.registerUser(request);
        } catch (IllegalArgumentException e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserRegistrationResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId the ID of the user to retrieve
     * @return ResponseEntity containing the user data
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        logger.info("Fetching user with ID: {}", userId);
        User user = getService().findById(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Updates user information.
     *
     * @param userId  the ID of the user to update
     * @param request the user update request containing updated data
     * @return ResponseEntity with updated user response
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserRegistrationResponse> updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequest request) {
        logger.info("Updating user with ID: {}", userId);

        try {
            User updatedUser = getService().update(userId, request);
            UserRegistrationResponse response = new UserRegistrationResponse();
            response.setMessage("User updated successfully");
            return ResponseEntity.ok(response);
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
     * @return ResponseEntity indicating the result of the deletion operation
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDeletionResponse> deleteUser(@PathVariable Long userId) {
        logger.info("Deleting user with ID: {}", userId);

        try {
            getService().delete(userId);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserDeletionResponse(e.getMessage()));
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
}
