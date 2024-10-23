package com.kraya.platform.service.impl;

import com.kraya.platform.model.Role;
import com.kraya.platform.repository.RoleRepository;
import com.kraya.platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RoleServiceImpl provides the implementation for role-related operations.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + id));
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Long id, Role role) {
        Role existingRole = findById(id);
        existingRole.setName(role.getName());
        return roleRepository.save(existingRole);
    }

    @Override
    public void delete(Long id) {
        Role role = findById(id);
        roleRepository.delete(role);
    }
}
