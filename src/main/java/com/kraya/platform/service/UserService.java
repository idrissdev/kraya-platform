package com.kraya.platform.service;

import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.dto.UserRegistrationResponse;
import com.kraya.platform.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<UserRegistrationResponse> registerUser(UserRegistrationRequest request);
    ResponseEntity<User> getUserById(Long userId);
    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<User> updateUser(Long userId, UserRegistrationRequest request);
    ResponseEntity<Void> deleteUser(Long userId);
}