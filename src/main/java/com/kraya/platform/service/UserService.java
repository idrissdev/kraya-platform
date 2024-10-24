package com.kraya.platform.service;

import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.dto.UserRegistrationResponse;
import com.kraya.platform.dto.UserUpdateRequest;
import com.kraya.platform.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * UserService interface defines the contract for user-related operations.
 */
public interface UserService {

    /**
     * Retrieves all users.
     *
     * @return a list of users
     */
    List<User> findAll();

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user object if found
     */
    User findById(Long id);

    /**
     * Creates a new user based on the registration request.
     *
     * @param request the user registration request containing necessary data
     * @return the created user
     */
    User create(UserRegistrationRequest request);

    /**
     * Updates an existing user's information.
     *
     * @param id     the ID of the user to update
     * @param request the updated user data
     * @return the updated user
     */
    User update(Long id, UserUpdateRequest request);

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    void delete(Long id);

    /**
     * Registers a new user and returns a registration response.
     *
     * @param request the user registration request
     * @return ResponseEntity containing the registration response
     */
    ResponseEntity<UserRegistrationResponse> registerUser(UserRegistrationRequest request);
}
