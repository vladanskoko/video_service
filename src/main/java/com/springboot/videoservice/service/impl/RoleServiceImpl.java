package com.springboot.videoservice.service.impl;

import com.springboot.videoservice.exception.ResourceNotFoundException;
import com.springboot.videoservice.model.Role;
import com.springboot.videoservice.repository.RoleRepository;
import com.springboot.videoservice.service.RoleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "Id", id));
        return role;
    }

    @Override
    public Role updateRole(@NotNull Role role, Long id) {
        Role existingRole = getRoleById(id);
        existingRole.setName(role.getName());
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Long id) {
        getRoleById(id);
        roleRepository.deleteById(id);
    }
}
