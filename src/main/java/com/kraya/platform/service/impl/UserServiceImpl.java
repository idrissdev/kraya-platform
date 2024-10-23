package com.kraya.platform.service.impl;

import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.dto.UserRegistrationResponse;
import com.kraya.platform.model.Roles;
import com.kraya.platform.model.User;
import com.kraya.platform.repository.UserRepository;
import com.kraya.platform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<UserRegistrationResponse> registerUser(UserRegistrationRequest request) {
        logger.info("Registering user with username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            logger.warn("Username {} already exists", request.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserRegistrationResponse("Username already exists"));
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Email {} already exists", request.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserRegistrationResponse("Email already exists"));
        }

        // Validate role
        try {
            Roles.valueOf(request.getRole());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid role: {}", request.getRole());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserRegistrationResponse("Invalid role"));
        }

        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Roles.valueOf(request.getRole()));

        var savedUser = userRepository.save(user);

        var response = new UserRegistrationResponse();
        response.setUserId(savedUser.getUserId());
        response.setMessage("User registered successfully");

        logger.info("User registered successfully with ID: {}", savedUser.getUserId());
        return ResponseEntity.created(URI.create("/users/" + savedUser.getUserId())).body(response);
    }

    @Override
    public ResponseEntity<User> getUserById(Long userId) {
        logger.info("Retrieving user with ID: {}", userId);
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Retrieving all users");
        var users = userRepository.findAll();
        return ResponseEntity.ok().body(users);
    }

    @Override
    public ResponseEntity<User> updateUser(Long userId, UserRegistrationRequest request) {
        logger.info("Updating user with ID: {}", userId);
        return userRepository.findById(userId)
                .map(user -> {
                    user.setUsername(request.getUsername());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setRole(Roles.valueOf(request.getRole()));
                    var updatedUser = userRepository.save(user);
                    return ResponseEntity.ok().body(updatedUser);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long userId) {
        logger.info("Deleting user with ID: {}", userId);
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}