package com.kraya.platform.service;

import com.kraya.platform.model.Role;

import java.util.List;

/**
 * RoleService interface defines the contract for role-related operations.
 */
public interface RoleService {

    /**
     * Retrieves all roles.
     *
     * @return a list of roles
     */
    List<Role> findAll();

    /**
     * Retrieves a role by ID.
     *
     * @param id the ID of the role to retrieve
     * @return the role if found
     */
    Role findById(Long id);

    /**
     * Creates a new role.
     *
     * @param role the role to create
     * @return the created role
     */
    Role create(Role role);

    /**
     * Updates an existing role.
     *
     * @param id   the ID of the role to update
     * @param role the updated role data
     * @return the updated role
     */
    Role update(Long id, Role role);

    /**
     * Deletes a role by ID.
     *
     * @param id the ID of the role to delete
     */
    void delete(Long id);
}
