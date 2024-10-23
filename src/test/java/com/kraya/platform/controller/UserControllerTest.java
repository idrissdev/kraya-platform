package com.kraya.platform.controller;

import com.kraya.platform.TestSecurityConfig;
import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.dto.UserRegistrationResponse;
import com.kraya.platform.dto.UserUpdateRequest;
import com.kraya.platform.exception.InvalidRoleException;
import com.kraya.platform.exception.UserNotFoundException;
import com.kraya.platform.model.User;
import com.kraya.platform.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/*

@WebMvcTest(UserController.class)
@WithMockUser// Assign multiple roles as needed}
*/

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class, TestSecurityConfig.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserRegistrationRequest registrationRequest;
    private UserUpdateRequest updateRequest;

    @BeforeEach
    public void setUp() {
        registrationRequest = new UserRegistrationRequest();
        registrationRequest.setUsername("testuser");
        registrationRequest.setPassword("password123");
        registrationRequest.setEmail("testuser@example.com");
        registrationRequest.setFirstName("Test");
        registrationRequest.setLastName("User");
        registrationRequest.setRole("USER");

        updateRequest = new UserUpdateRequest();
        updateRequest.setUsername("updateduser");
        updateRequest.setEmail("updateduser@example.com");
        updateRequest.setFirstName("Updated");
        updateRequest.setLastName("User");
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setUserId(1L);
        response.setMessage("User registered successfully");

        when(userService.registerUser(any(UserRegistrationRequest.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"password\":\"password123\",\"email\":\"testuser@example.com\",\"firstName\":\"Test\",\"lastName\":\"User\",\"role\":\"USER\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    public void testRegisterUserWithDuplicateUsername() throws Exception {
        when(userService.registerUser(any(UserRegistrationRequest.class))).thenThrow(new IllegalArgumentException("Username already exists."));

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"password\":\"password123\",\"email\":\"testuser@example.com\",\"firstName\":\"Test\",\"lastName\":\"User\",\"role\":\"USER\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Username already exists."));
    }

    @Test
    public void testRegisterUserWithDuplicateEmail() throws Exception {
        when(userService.registerUser(any(UserRegistrationRequest.class))).thenThrow(new IllegalArgumentException("Email already exists."));

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"password\":\"password123\",\"email\":\"testuser@example.com\",\"firstName\":\"Test\",\"lastName\":\"User\",\"role\":\"USER\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email already exists."));
    }

    @Test
    public void testRegisterUserWithBlankUsername() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"\",\"password\":\"password123\",\"email\":\"testuser@example.com\",\"firstName\":\"Test\",\"lastName\":\"User\",\"role\":\"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username is mandatory"));
    }

    @Test
    public void testRegisterUserWithShortPassword() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"password\":\"123\",\"email\":\"testuser@example.com\",\"firstName\":\"Test\",\"lastName\":\"User\",\"role\":\"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must be at least 6 characters long"));
    }

    @Test
    public void testRegisterUserWithInvalidEmail() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"password\":\"password123\",\"email\":\"invalid-email\",\"firstName\":\"Test\",\"lastName\":\"User\",\"role\":\"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email should be valid"));
    }

    @Test
    public void testRegisterUserWithBlankFirstName() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"password\":\"password123\",\"email\":\"testuser@example.com\",\"firstName\":\"\",\"lastName\":\"User\",\"role\":\"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First name is mandatory"));
    }

    @Test
    public void testRegisterUserWithBlankLastName() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"password\":\"password123\",\"email\":\"testuser@example.com\",\"firstName\":\"Test\",\"lastName\":\"\",\"role\":\"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastName").value("Last name is mandatory"));
    }

    @Test
    public void testRegisterUserWithBlankRole() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"password\":\"password123\",\"email\":\"testuser@example.com\",\"firstName\":\"Test\",\"lastName\":\"User\",\"role\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid role"));
    }

    @Test
    public void testRegisterUserWithInvalidRole() throws Exception {
        when(userService.registerUser(any(UserRegistrationRequest.class))).thenThrow(new InvalidRoleException("Invalid role"));

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"password\":\"password123\",\"email\":\"testuser@example.com\",\"firstName\":\"Test\",\"lastName\":\"User\",\"role\":\"INVALID_ROLE\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid role"));
    }

    @Test
    public void testGetUserByIdSuccess() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setFirstName("Test");
        user.setLastName("User");

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        when(userService.findById(999L)).thenThrow(new UserNotFoundException("User not found with ID: 999"));

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with ID: 999"));
    }

    @Test
    public void testUpdateUserSuccess() throws Exception {
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setMessage("User updated successfully");

        when(userService.update(1L, any(UserUpdateRequest.class))).thenReturn(new User());

        mockMvc.perform(put("/api/users/1")
                        .contentType("application/json")
                        .content("{\"username\":\"updateduser\",\"email\":\"updateduser@example.com\",\"firstName\":\"Updated\",\"lastName\":\"User\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User updated successfully"));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        when(userService.update(999L, any(UserUpdateRequest.class))).thenThrow(new UserNotFoundException("User not found with ID: 999"));

        mockMvc.perform(put("/api/users/999")
                        .contentType("application/json")
                        .content("{\"username\":\"updateduser\",\"email\":\"updateduser@example.com\",\"firstName\":\"Updated\",\"lastName\":\"User\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with ID: 999"));
    }

    @Test
    public void testDeleteUserSuccess() throws Exception {
        Mockito.doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        doThrow(new UserNotFoundException("User not found with ID: 999")).when(userService).delete(999L);

        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with ID: 999"));
    }
}
