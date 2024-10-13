package com.kraya.platform.controller;

import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.dto.UserRegistrationResponse;
import com.kraya.platform.model.User;
import com.kraya.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller for user-related operations.
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user.
     *
     * @param request the user registration request
     * @return a ResponseEntity containing the registration response
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }

    /**
     * Retrieves the details of a user by their ID.
     *
     * @param userId the ID of the user
     * @return a ResponseEntity containing the user details
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a ResponseEntity containing the list of users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Updates the information of an existing user.
     *
     * @param userId the ID of the user to update
     * @param request the user update request
     * @return a ResponseEntity containing the updated user details
     */
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRegistrationRequest request) {
        return userService.updateUser(userId, request);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     * @return a ResponseEntity indicating the result of the operation
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}