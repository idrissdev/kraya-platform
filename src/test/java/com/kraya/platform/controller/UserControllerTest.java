package com.kraya.platform.controller;

import com.kraya.platform.config.SecurityConfig;
import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.model.Roles;
import com.kraya.platform.model.User;
import com.kraya.platform.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for the UserController class.
 */
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    /**
     * Test for successful user registration.
     */
    @Test
    public void testRegisterUserSuccess() throws Exception {
        // Prepare test data
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        request.setEmail("testuser@example.com");
        request.setFirstName("Test");
        request.setLastName("User");
        request.setRole("USER");

        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setUsername("testuser");
        savedUser.setPassword("password123");
        savedUser.setEmail("testuser@example.com");
        savedUser.setFirstName("Test");
        savedUser.setLastName("User");
        savedUser.setRole(Roles.USER);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        // Perform request and verify response
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\", \"email\": \"testuser@example.com\", \"firstName\": \"Test\", \"lastName\": \"User\", \"role\": \"USER\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/1"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    /**
     * Test for user registration with a blank username.
     */
    @Test
    public void testRegisterUserWithBlankUsername() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"\", \"password\": \"password123\", \"email\": \"testuser@example.com\", \"firstName\": \"Test\", \"lastName\": \"User\", \"role\": \"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username is required"));
    }

    /**
     * Test for user registration with a short password.
     */
    @Test
    public void testRegisterUserWithShortPassword() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"123\", \"email\": \"testuser@example.com\", \"firstName\": \"Test\", \"lastName\": \"User\", \"role\": \"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must be at least 6 characters"));
    }

    /**
     * Test for user registration with an invalid email.
     */
    @Test
    public void testRegisterUserWithInvalidEmail() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\", \"email\": \"invalid-email\", \"firstName\": \"Test\", \"lastName\": \"User\", \"role\": \"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Invalid email format"));
    }

    /**
     * Test for user registration with a blank first name.
     */
    @Test
    public void testRegisterUserWithBlankFirstName() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\", \"email\": \"testuser@example.com\", \"firstName\": \"\", \"lastName\": \"User\", \"role\": \"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First name is required"));
    }

    /**
     * Test for user registration with a blank last name.
     */
    @Test
    public void testRegisterUserWithBlankLastName() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\", \"email\": \"testuser@example.com\", \"firstName\": \"Test\", \"lastName\": \"\", \"role\": \"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastName").value("Last name is required"));
    }

    /**
     * Test for user registration with a blank role.
     */
    @Test
    public void testRegisterUserWithBlankRole() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\", \"email\": \"testuser@example.com\", \"firstName\": \"Test\", \"lastName\": \"User\", \"role\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid role"));
    }

    /**
     * Test for user registration with a duplicate username.
     */
    @Test
    public void testRegisterUserWithDuplicateUsername() throws Exception {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\", \"email\": \"testuser@example.com\", \"firstName\": \"Test\", \"lastName\": \"User\", \"role\": \"USER\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Username already exists"));
    }

    /**
     * Test for user registration with a duplicate email.
     */
    @Test
    public void testRegisterUserWithDuplicateEmail() throws Exception {
        when(userRepository.existsByEmail("testuser@example.com")).thenReturn(true);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\", \"email\": \"testuser@example.com\", \"firstName\": \"Test\", \"lastName\": \"User\", \"role\": \"USER\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email already exists"));
    }

    /**
     * Test for user registration with an invalid role.
     */
    @Test
    public void testRegisterUserWithInvalidRole() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\", \"email\": \"testuser@example.com\", \"firstName\": \"Test\", \"lastName\": \"User\", \"role\": \"INVALID_ROLE\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid role"));
    }
}