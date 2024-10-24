package com.kraya.platform.service.impl;

import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.dto.UserRegistrationResponse;
import com.kraya.platform.dto.UserUpdateRequest;
import com.kraya.platform.exception.InvalidRoleException;
import com.kraya.platform.exception.UserNotFoundException;
import com.kraya.platform.model.AppUser;  // Importing AppUser
import com.kraya.platform.model.Roles;
import com.kraya.platform.model.User;
import com.kraya.platform.repository.UserRepository;
import com.kraya.platform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

/**
 * UserServiceImpl provides the implementation for user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    @Override
    public User create(UserRegistrationRequest request) {
        // Validate if the user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        // Create an AppUser instance
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber()); // Optional
        user.setProfilePictureUrl(request.getProfilePictureUrl()); // Optional
        user.setStatus(User.Status.ACTIVE); // Default status
        user.setRegistrationDate(LocalDateTime.now()); // Set registration date

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, UserUpdateRequest request) {
        User user = findById(id);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user.setPhoneNumber(request.getPhoneNumber()); // Update optional fields
        user.setProfilePictureUrl(request.getProfilePictureUrl()); // Update optional fields

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Override
    public ResponseEntity<UserRegistrationResponse> registerUser(UserRegistrationRequest request) {
        logger.info("Registering user: {}", request.getUsername());
        AppUser createdUser = (AppUser) create(request);
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setUserId(createdUser.getUserId());
        response.setMessage("User registered successfully");
        return ResponseEntity.created(URI.create("/api/users/" + createdUser.getUserId())).body(response);
    }
}
