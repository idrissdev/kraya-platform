package com.kraya.platform.controller;

import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.dto.UserRegistrationResponse;
import com.kraya.platform.dto.UserUpdateRequest;
import com.kraya.platform.exception.UserNotFoundException;
import com.kraya.platform.model.AppUser;
import com.kraya.platform.model.User;
import com.kraya.platform.repository.UserRepository;
import com.kraya.platform.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById_ExistingUser() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertEquals(1L, result.getUserId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findById_NonExistingUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void create_NewUser() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        request.setEmail("testuser@example.com");
        request.setFirstName("Test");
        request.setLastName("User");
        request.setPhoneNumber("1234567890");
        request.setProfilePictureUrl("https://example.com/profile.jpg");

        AppUser user = new AppUser();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setEmail("testuser@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPhoneNumber("1234567890");
        user.setProfilePictureUrl("https://example.com/profile.jpg");
        user.setStatus(User.Status.ACTIVE);
        user.setRegistrationDate(LocalDateTime.now());

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("testuser@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        AppUser result = (AppUser) userService.create(request);

        assertEquals(1L, result.getUserId());
        assertEquals("testuser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("testuser@example.com", result.getEmail());
        assertEquals("Test", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("1234567890", result.getPhoneNumber());
        assertEquals("https://example.com/profile.jpg", result.getProfilePictureUrl());
        assertEquals(User.Status.ACTIVE, result.getStatus());
        assertNotNull(result.getRegistrationDate());

        verify(userRepository, times(1)).existsByUsername("testuser");
        verify(userRepository, times(1)).existsByEmail("testuser@example.com");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    void create_DuplicateUsername() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        request.setEmail("testuser@example.com");
        request.setFirstName("Test");
        request.setLastName("User");

        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.create(request));

        verify(userRepository, times(1)).existsByUsername("testuser");
        verify(userRepository, never()).existsByEmail("testuser@example.com");
        verify(passwordEncoder, never()).encode("password123");
        verify(userRepository, never()).save(any(AppUser.class));
    }

    @Test
    void create_DuplicateEmail() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        request.setEmail("testuser@example.com");
        request.setFirstName("Test");
        request.setLastName("User");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("testuser@example.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.create(request));

        verify(userRepository, times(1)).existsByUsername("testuser");
        verify(userRepository, times(1)).existsByEmail("testuser@example.com");
        verify(passwordEncoder, never()).encode("password123");
        verify(userRepository, never()).save(any(AppUser.class));
    }

    @Test
    void update_ExistingUser() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setEmail("testuser@example.com");
        user.setFirstName("Test");
        user.setLastName("User");

        UserUpdateRequest request = new UserUpdateRequest();
        request.setUsername("updateduser");
        request.setEmail("updateduser@example.com");
        request.setFirstName("Updated");
        request.setLastName("User");
        request.setPassword("newpassword123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newpassword123")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.update(1L, request);

        assertEquals(1L, result.getUserId());
        assertEquals("updateduser", result.getUsername());
        assertEquals("encodedNewPassword", result.getPassword());
        assertEquals("updateduser@example.com", result.getEmail());
        assertEquals("Updated", result.getFirstName());
        assertEquals("User", result.getLastName());

        verify(userRepository, times(1)).findById(1L);
        verify(passwordEncoder, times(1)).encode("newpassword123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void update_NonExistingUser() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setUsername("updateduser");
        request.setEmail("updateduser@example.com");
        request.setFirstName("Updated");
        request.setLastName("User");
        request.setPassword("newpassword123");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.update(1L, request));

        verify(userRepository, times(1)).findById(1L);
        verify(passwordEncoder, never()).encode("newpassword123");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void delete_ExistingUser() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void delete_NonExistingUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(1L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void registerUser() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        request.setEmail("testuser@example.com");
        request.setFirstName("Test");
        request.setLastName("User");
        request.setPhoneNumber("1234567890");
        request.setProfilePictureUrl("https://example.com/profile.jpg");

        AppUser user = new AppUser();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setEmail("testuser@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPhoneNumber("1234567890");
        user.setProfilePictureUrl("https://example.com/profile.jpg");
        user.setStatus(User.Status.ACTIVE);
        user.setRegistrationDate(LocalDateTime.now());

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("testuser@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        ResponseEntity<UserRegistrationResponse> response = userService.registerUser(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/users/1", response.getHeaders().getLocation().getPath());

        UserRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(1L, responseBody.getUserId());
        assertEquals("User registered successfully", responseBody.getMessage());

        verify(userRepository, times(1)).existsByUsername("testuser");
        verify(userRepository, times(1)).existsByEmail("testuser@example.com");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(AppUser.class));
    }
}