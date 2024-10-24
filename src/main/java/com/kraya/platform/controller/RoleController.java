package com.kraya.platform.controller;

import com.kraya.platform.dto.RoleRequest;
import com.kraya.platform.dto.RoleResponse;
import com.kraya.platform.model.Role;
import com.kraya.platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * RoleController handles role-related HTTP requests.
 */
@RestController
@RequestMapping("/api/roles")  // Base URL for role-related operations
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = roleService.findAll().stream()
                .map(role -> {
                    RoleResponse response = new RoleResponse();
                    response.setId(role.getId());
                    response.setName(role.getName());
                    return response;
                })
                .toList();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        Role role = roleService.findById(id);
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request) {
        Role createdRole = roleService.create(new Role(request.getName()));
        RoleResponse response = new RoleResponse();
        response.setId(createdRole.getId());
        response.setName(createdRole.getName());
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        Role updatedRole = roleService.update(id, new Role(request.getName()));
        RoleResponse response = new RoleResponse();
        response.setId(updatedRole.getId());
        response.setName(updatedRole.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
